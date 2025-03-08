package com.anning.projectimage.weight;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.appcompat.widget.AppCompatImageView;

public class TransformImageView extends AppCompatImageView {
    // 缩放相关常量
    private static final float MIN_SCALE = 0.5f;
    private static final float MID_SCALE = 2.0f;
    private static final float MAX_SCALE = 3.0f;
    private static final int ZOOM_DURATION = 300;

    // 矩阵相关
    private Matrix baseMatrix = new Matrix(); // 基础矩阵：将图片初始化为控件大小，并居中显示
    private Matrix suppMatrix = new Matrix(); // 附加矩阵：存储用户手势操作（缩放、移动）后的变换
    private final float[] matrixValues = new float[9]; // 矩阵值缓存
    private final RectF displayRect = new RectF(); // 显示区域

    // 手势检测器
    private GestureDetector gestureDetector; // 用于检测移动手势
    private ScaleGestureDetector scaleGestureDetector; // 用于检测缩放手势

    // 用于存储触摸点
    private PointF lastTouch = new PointF();
    private float[] lastEvent = null;

    // 动画相关
    private Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private boolean isAnimating = false;

    // 用于标记是否已经初始化
    private boolean isInitialized = false;

    public TransformImageView(Context context) {
        super(context);
        init(context);
    }

    public TransformImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TransformImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 设置缩放类型为 MATRIX，以便通过 Matrix 控制图片变换
        setScaleType(ScaleType.MATRIX);
        // 初始化 GestureDetector
        gestureDetector = new GestureDetector(context, new GestureListener());
        // 初始化 ScaleGestureDetector
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 初次加载时，调整图片大小以适应控件
        if (!isInitialized && getDrawable() != null) {
            initializeImage();
            isInitialized = true;
        }
    }

    private void initializeImage() {
        updateBaseMatrix(getDrawable());
        setImageMatrix(getDrawMatrix());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将触摸事件传递给 ScaleGestureDetector 和 GestureDetector
        boolean handled = scaleGestureDetector.onTouchEvent(event);
        handled = gestureDetector.onTouchEvent(event) || handled;

        // 处理多点触控
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // 记录单点触摸位置
                lastTouch.set(event.getX(), event.getY());
                lastEvent = null;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    // 记录多点触摸位置
                    lastEvent = new float[4];
                    lastEvent[0] = event.getX(0);
                    lastEvent[1] = event.getY(0);
                    lastEvent[2] = event.getX(1);
                    lastEvent[3] = event.getY(1);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!scaleGestureDetector.isInProgress()) {
                    if (event.getPointerCount() == 1) {
                        // 计算移动距离
                        float dx = event.getX() - lastTouch.x;
                        float dy = event.getY() - lastTouch.y;

                        // 应用移动变换
                        suppMatrix.postTranslate(dx, dy);
                        checkAndDisplayMatrix();

                        // 更新触摸点位置
                        lastTouch.set(event.getX(), event.getY());
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                lastEvent = null;
                // 检查是否需要修正缩放比例
                float scale = getScale();
                if (scale < MIN_SCALE) {
                    post(new AnimatedZoomRunnable(scale, MIN_SCALE, getWidth() / 2f, getHeight() / 2f));
                } else if (scale > MAX_SCALE) {
                    post(new AnimatedZoomRunnable(scale, MAX_SCALE, getWidth() / 2f, getHeight() / 2f));
                }
                break;
        }

        return true;
    }

    // 手势监听器
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (scaleGestureDetector.isInProgress()) {
                return false;
            }
            // 应用移动变换
            suppMatrix.postTranslate(-distanceX, -distanceY);
            checkAndDisplayMatrix();
            return true;
        }

        // 双击缩放
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float scale = getScale();
            float x = e.getX();
            float y = e.getY();

            if (scale < MID_SCALE) {
                post(new AnimatedZoomRunnable(scale, MID_SCALE, x, y));
            } else if (scale < MAX_SCALE) {
                post(new AnimatedZoomRunnable(scale, MAX_SCALE, x, y));
            } else {
                post(new AnimatedZoomRunnable(scale, MIN_SCALE, x, y));
            }
            return true;
        }
    }

    // 缩放监听器
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // 获取缩放因子
            float scaleFactor = detector.getScaleFactor();

            // 获取当前缩放比例
            float currentScale = getScale();

            // 限制缩放范围
            if ((currentScale * scaleFactor < MIN_SCALE && scaleFactor < 1f) ||
                    (currentScale * scaleFactor > MAX_SCALE && scaleFactor > 1f)) {
                scaleFactor = 1f;
            }

            // 获取两指的中心点
            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();

            // 应用缩放变换（以两指的中心点为缩放中心）
            suppMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
            checkAndDisplayMatrix();

            return true;
        }
    }

    // 获取当前缩放比例
    private float getScale() {
        suppMatrix.getValues(matrixValues);
        return (float) Math.sqrt(Math.pow(matrixValues[Matrix.MSCALE_X], 2) +
                Math.pow(matrixValues[Matrix.MSKEW_Y], 2));
    }

    /**
     * 获取绘制矩阵
     * desc:获取最终的绘制矩阵，由 baseMatrix 和 suppMatrix 组合而成
     */
    private Matrix getDrawMatrix() {
        Matrix drawMatrix = new Matrix();
        drawMatrix.set(baseMatrix);
        drawMatrix.postConcat(suppMatrix);
        return drawMatrix;
    }

    /**
     * 检查矩阵边界并显示
     * desc:检查矩阵的边界，并更新图片的显示
     */
    private boolean checkAndDisplayMatrix() {
        if (checkMatrixBounds()) {
            setImageMatrix(getDrawMatrix());
            return true;
        }
        return false;
    }

    /**
     * 检查矩阵边界
     * desc:检查图片是否超出控件边界，并修正图片的位置
     * 检查图片是否超出控件边界，并修正图片的位置。
     * 如果图片的宽度或高度小于控件的大小，则居中显示。
     * 如果图片的边界超出控件，则调整图片的位置。
     */
    private boolean checkMatrixBounds() {
        final RectF rect = getDisplayRect(getDrawMatrix());
        if (rect == null) {
            return false;
        }

        final float height = rect.height();
        final float width = rect.width();
        float deltaX = 0, deltaY = 0;

        final int viewHeight = getHeight();
        if (height <= viewHeight) {
            deltaY = (viewHeight - height) / 2 - rect.top;
        } else if (rect.top > 0) {
            deltaY = -rect.top;
        } else if (rect.bottom < viewHeight) {
            deltaY = viewHeight - rect.bottom;
        }

        final int viewWidth = getWidth();
        if (width <= viewWidth) {
            deltaX = (viewWidth - width) / 2 - rect.left;
        } else if (rect.left > 0) {
            deltaX = -rect.left;
        } else if (rect.right < viewWidth) {
            deltaX = viewWidth - rect.right;
        }

        // 应用边界修正
        suppMatrix.postTranslate(deltaX, deltaY);
        return true;
    }

    // 获取显示区域
    private RectF getDisplayRect(Matrix matrix) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            displayRect.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(displayRect);
            return displayRect;
        }
        return null;
    }

    // 更新基础矩阵
    private void updateBaseMatrix(Drawable drawable) {
        if (drawable == null) {
            return;
        }

        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        final int drawableWidth = drawable.getIntrinsicWidth();
        final int drawableHeight = drawable.getIntrinsicHeight();

        // 初始化矩阵：将图片缩放并居中显示
        baseMatrix.reset();

        // 计算缩放比例，使图片适应控件
        float scale = Math.min(viewWidth / drawableWidth, viewHeight / drawableHeight);

        // 应用缩放
        baseMatrix.postScale(scale, scale);

        // 居中显示
        float redundantXSpace = viewWidth - (scale * drawableWidth);
        float redundantYSpace = viewHeight - (scale * drawableHeight);
        baseMatrix.postTranslate(redundantXSpace / 2, redundantYSpace / 2);

        // 重置附加矩阵
        suppMatrix.reset();
    }

    // 动画缩放实现
    private class AnimatedZoomRunnable implements Runnable {
        private final float startScale;
        private final float targetScale;
        private final float focalX;
        private final float focalY;
        private final long startTime;

        public AnimatedZoomRunnable(float startScale, float targetScale, float focalX, float focalY) {
            this.startScale = startScale;
            this.targetScale = targetScale;
            this.focalX = focalX;
            this.focalY = focalY;
            this.startTime = System.currentTimeMillis();
            isAnimating = true;
        }

        @Override
        public void run() {
            float t = interpolate();
            float scale = startScale + t * (targetScale - startScale);
            float scaleFactor = scale / getScale();

            suppMatrix.postScale(scaleFactor, scaleFactor, focalX, focalY);
            checkAndDisplayMatrix();

            if (t < 1f) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    TransformImageView.this.postOnAnimation(this);
                } else {
                    TransformImageView.this.postDelayed(this, 16);
                }
            } else {
                isAnimating = false;
            }
        }

        private float interpolate() {
            float t = Math.min(1f, (System.currentTimeMillis() - startTime) / (float) ZOOM_DURATION);
            return interpolator.getInterpolation(t);
        }
    }
}