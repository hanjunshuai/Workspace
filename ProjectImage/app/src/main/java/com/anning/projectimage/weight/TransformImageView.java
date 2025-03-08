package com.anning.projectimage.weight;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import androidx.appcompat.widget.AppCompatImageView;

public class TransformImageView extends AppCompatImageView {

    private Matrix matrix = new Matrix(); // 用于存储变换矩阵
    private ScaleGestureDetector scaleGestureDetector; // 用于检测缩放手势
    private GestureDetector gestureDetector; // 用于检测移动和旋转手势

    // 用于存储触摸点
    private PointF lastTouch = new PointF();
    private float[] lastEvent = null;

    // 用于存储当前的缩放、旋转状态
    private float scaleFactor = 1.0f;
    private float rotationDegrees = 0.0f;

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

        // 初始化 ScaleGestureDetector
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());

        // 初始化 GestureDetector
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将触摸事件传递给 ScaleGestureDetector 和 GestureDetector
        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);

        // 处理多点触控
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // 记录单点触摸位置
                lastTouch.set(event.getX(), event.getY());
                lastEvent = null;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                // 记录多点触摸位置
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getY(0);
                lastEvent[2] = event.getX(1);
                lastEvent[3] = event.getY(1);
                break;

            case MotionEvent.ACTION_MOVE:
                if (!scaleGestureDetector.isInProgress()) {
                    if (event.getPointerCount() == 2 && lastEvent != null) {
                        // 计算旋转角度
                        float newX = event.getX(0) - event.getX(1);
                        float newY = event.getY(0) - event.getY(1);
                        float oldX = lastEvent[0] - lastEvent[2];
                        float oldY = lastEvent[1] - lastEvent[3];

                        float newAngle = (float) Math.toDegrees(Math.atan2(newY, newX));
                        float oldAngle = (float) Math.toDegrees(Math.atan2(oldY, oldX));

                        rotationDegrees += newAngle - oldAngle;

                        // 应用旋转变换
                        matrix.postRotate(newAngle - oldAngle, getWidth() / 2f, getHeight() / 2f);
                        setImageMatrix(matrix);
                    } else if (event.getPointerCount() == 1) {
                        // 计算移动距离
                        float dx = event.getX() - lastTouch.x;
                        float dy = event.getY() - lastTouch.y;

                        // 应用移动变换
                        matrix.postTranslate(dx, dy);
                        setImageMatrix(matrix);

                        // 更新触摸点位置
                        lastTouch.set(event.getX(), event.getY());
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                lastEvent = null;
                break;
        }

        return true;
    }

    // 缩放监听器
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // 获取缩放因子
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f)); // 限制缩放范围

            // 应用缩放变换
            matrix.setScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            setImageMatrix(matrix);

            return true;
        }
    }

    // 手势监听器
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // 应用移动变换
            matrix.postTranslate(-distanceX, -distanceY);
            setImageMatrix(matrix);
            return true;
        }
    }
}