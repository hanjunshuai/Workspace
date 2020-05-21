package com.anningtex.messagedrag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


/**
 * @ClassName: MessageDragView
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/5/21 9:37
 */
public class MessageDragView extends View {
    private PointF mFixedPointF, mDragPointF;
    private Paint mPaint;
    private int mDragRadius = 10;
    private int mFixedRadiusMin = 2;
    private int mFixedRadiusMax = 8;
    private int mFixedRadius;


    public MessageDragView(Context context) {
        super(context);
        initPaint();
    }

    public MessageDragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MessageDragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mDragRadius = dip2px(mDragRadius);
        mFixedRadius = dip2px(mFixedRadiusMax);
        mFixedRadiusMin = dip2px(mFixedRadiusMin);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    private int dip2px(float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFixedPointF == null && mDragPointF == null) {
            return;
        }
        // 拖拽圆
        canvas.drawCircle(mDragPointF.x, mDragPointF.y, mDragRadius, mPaint);

        // 固定圆
        float distance = (float) getDistance(mFixedPointF, mDragPointF);
        mFixedRadius = dip2px(mFixedRadiusMax - distance / 14);
        if (mFixedRadius > mFixedRadiusMin) {
            canvas.drawCircle(mFixedPointF.x, mFixedPointF.y, mFixedRadius, mPaint);
        }

    }

    private double getDistance(PointF point1, PointF point2) {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX();
                float downY = event.getY();
                initFixedPointF(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                movePointF(moveX, moveY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + event.getAction());
        }
        invalidate();
        return true;
    }

    private void movePointF(float moveX, float moveY) {
        mDragPointF = new PointF(moveX, moveY);
    }

    private void initFixedPointF(float downX, float downY) {
        mFixedPointF = new PointF(downX, downY);
        mDragPointF = new PointF(downX, downY);
    }
}
