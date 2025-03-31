package com.anningtex.buildapkproject.sticker;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

public class DecorationElementContainerView extends ElementContainerView {
    private static final String TAG = "heshixi:DECV";

    protected DecorationActionMode mDecorationActionMode;
    protected boolean mIsRunOnFlingAnimation = true;

    public DecorationElementContainerView(Context context) {
        super(context);
    }

    public DecorationElementContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DecorationElementContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public DecorationElementContainerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /**
     * 取消选中、删除
     */
    public void unSelectDeleteAndUpdateTopElement() {
        unSelectElement();
        deleteElement();
    }

    /**
     * 添加、选中、更新
     *
     * @param wsElement
     */
    public void addSelectAndUpdateElement(WsElement wsElement) {
        addElement(wsElement);
        selectElement(wsElement);
        update();
    }

    @Override
    protected boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!mIsRunOnFlingAnimation) {
            return false;
        }

        DecorationElement element = (DecorationElement) mSelectedElement;
        if (element == null) {
            return false;
        }

        element = (DecorationElement) findElementByPosition(e2.getX(), e2.getY());
        if (element == null) {
            return false;
        }

        AnimationElement.TransformParam to = new AnimationElement.TransformParam(element);
        to.mMoveY += (velocityY * 0.2 * 0.3);
        to.mMoveX += (velocityX * 0.2 * 0.3);
        element.startElementAnimation(to);
        return true;
    }

    @Override
    protected boolean downSelectTapOtherAction(@NonNull MotionEvent event) {
        mDecorationActionMode = DecorationActionMode.NONE;
        final float x = event.getX(), y = event.getY();
        DecorationElement selectedDecorationElement = (DecorationElement) mSelectedElement;
        if (selectedDecorationElement.isInScaleAndRotateButton(x, y)) {
            // 开始进行单指旋转缩放
            mDecorationActionMode = DecorationActionMode.SINGER_FINGER_SCALE_AND_ROTATE;
            selectedDecorationElement.onSingleFingerScaleAndRotateStart();
            callListener(elementActionListener -> {
                ((DecorationElementActionListener) elementActionListener).onSingleFingerScaleAndRotateStart(selectedDecorationElement);
            });
            Log.d(TAG, "downSelectTapOtherAction selected scale and rotate");
            return true;
        }
        if (selectedDecorationElement.isInRemoveButton(x, y)) {
            mDecorationActionMode = DecorationActionMode.CLICK_BUTTON_DELETE;
            Log.d(TAG, "downSelectTapOtherAction selected delete");
            return true;
        }
        return false;
    }

    @Override
    protected boolean scrollSelectTapOtherAction(@NonNull MotionEvent event, float[] distance) {
        if (mSelectedElement == null) {
            Log.d(TAG, "detectorSingleFingerRotateAndScale scale and rotate but not select");
            return false;
        }

        if (mDecorationActionMode == DecorationActionMode.CLICK_BUTTON_DELETE) {
            return true;
        }

        if (mDecorationActionMode == DecorationActionMode.SINGER_FINGER_SCALE_AND_ROTATE) {
            DecorationElement selectedDecorationElement = (DecorationElement) mSelectedElement;
            selectedDecorationElement.onSingleFingerScaleAndRotateProcess(event.getX(), event.getY());
            update();
            // 在单指旋转缩放过程中
            callListener(elementActionListener -> {
                ((DecorationElementActionListener) elementActionListener).onSingleFingerScaleAndRotateProcess(selectedDecorationElement);
            });
            Log.d(TAG,
                    "scrollSelectTapOtherAction scale and rotate |||||||||| distanceX:" + distance[0]
                            + "distanceY:" + distance[1] + "x:" + event.getX() + "y:" + event.getY());
            return true;
        }

        return false;
    }

    @Override
    protected boolean upSelectTapOtherAction(@NonNull MotionEvent event) {
        if (mSelectedElement == null) {
            Log.w(TAG, "upSelectTapOtherAction delete but not select ");
            return false;
        }

        DecorationElement selectedDecorationElement = (DecorationElement) mSelectedElement;
        if (mDecorationActionMode == DecorationActionMode.CLICK_BUTTON_DELETE
                && selectedDecorationElement.isInRemoveButton(event.getX(), event.getY())) {
            unSelectDeleteAndUpdateTopElement();
            mDecorationActionMode = DecorationActionMode.NONE;
            Log.d(TAG, "upSelectTapOtherAction delete");
            return true;
        }

        if (mDecorationActionMode == DecorationActionMode.SINGER_FINGER_SCALE_AND_ROTATE) {
            selectedDecorationElement.onSingleFingerScaleAndRotateEnd();
            callListener(elementActionListener -> {
                ((DecorationElementActionListener) elementActionListener).onSingleFingerScaleAndRotateEnd(selectedDecorationElement);
            });
            mDecorationActionMode = DecorationActionMode.NONE;
            Log.d(TAG, "upSelectTapOtherAction scale and rotate end");
            return true;
        }
        return false;
    }

    public interface DecorationElementActionListener extends ElementActionListener {
        /**
         * 选中了元素之后，对元素单指缩放旋转开始的回调
         *
         * @param element
         */
        void onSingleFingerScaleAndRotateStart(DecorationElement element);

        /**
         * 选中了元素之后，对元素单指缩放旋转过程的回调
         *
         * @param element
         */
        void onSingleFingerScaleAndRotateProcess(DecorationElement element);

        /**
         * 一次单指 缩放旋转 结束
         *
         * @param element
         */
        void onSingleFingerScaleAndRotateEnd(DecorationElement element);
    }

    public static class DefaultDecorationElementActionListener extends DefaultElementActionListener implements DecorationElementActionListener {

        @Override
        public void onSingleFingerScaleAndRotateStart(DecorationElement element) {
            Log.d(TAG, "onSingleFingerScaleAndRotateStart");
        }

        @Override
        public void onSingleFingerScaleAndRotateProcess(DecorationElement element) {
            Log.d(TAG, "onSingleFingerScaleAndRotateProcess");
        }

        @Override
        public void onSingleFingerScaleAndRotateEnd(DecorationElement element) {
            Log.d(TAG, "onSingleFingerScaleAndRotateEnd");
        }
    }

    public enum DecorationActionMode {
        NONE,
        SINGER_FINGER_SCALE_AND_ROTATE,
        CLICK_BUTTON_DELETE,
    }
}
