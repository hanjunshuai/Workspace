package com.anningtex.wanandroid.widget.web

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.anningtex.wanandroid.R

/**
 *
 * @ClassName:      WebProgressBar
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 8:35
 */
class WebProgressBar : View {
    private var progress: Int = 0
    private var mHeight: Int = 0
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        paint.style = Paint.Style.FILL
        paint.color = resources.getColor(R.color.colorAccent)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.strokeWidth = mHeight.toFloat()
        canvas?.drawLine(0f, 0f, width * progress / 100f, 0f, paint)
    }

    fun setProgress(newProgress: Int) {
        progress = newProgress
        invalidate()
    }

    fun getProgress(): Int {
        return progress
    }

}