package com.anningtex.wanandroid.project.weight

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 *
 * @ClassName:      ProjectViewPager
 * @Description:    使用 内部拦截法 解决滑动冲突
 * @Author:         alvis
 * @CreateDate:     2020/7/6 10:49
 */
class ProjectViewPager : ViewPager {
    private var downX: Float = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                parent.requestDisallowInterceptTouchEvent(true)
                return super.dispatchTouchEvent(ev)
            }

            MotionEvent.ACTION_MOVE -> {
                val moveX = ev.x
                val currentItem = currentItem
                val deltaX = moveX - downX
                // 右滑内部 ViewPager 第一页
                if (currentItem == 0 && deltaX > 0) {
                    parent.requestDisallowInterceptTouchEvent(false)
                } else if (currentItem == (adapter!!.count - 1) && deltaX < 0) {   //左滑内部 ViewPager 最后一页
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}