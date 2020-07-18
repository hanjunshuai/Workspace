package com.anningtex.anlogkotlin.weight

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.accessibility.AccessibilityEvent
import androidx.viewpager.widget.ViewPager

/**
 *
 * @ClassName:      CustomViewPager
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 14:52
 */
class CustomViewPager : ViewPager {
    var mIsPagingEnabled: Boolean = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return mIsPagingEnabled && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mIsPagingEnabled && super.onInterceptTouchEvent(ev)
    }

}