package com.anningtex.wanandroid.home.weight

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @ClassName:      WxPublicRecyclerView
 * @Description:    自定义 微信公众号 recyclerview
 * @Author:         alvis
 * @CreateDate:     2020/7/4 16:58
 */
class WxPublicRecyclerView : RecyclerView {
    private var downX = 0f
    private var downY = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                if (layoutManager is LinearLayoutManager) {
                    val firstVisiblePosition =
                        (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                    Log.e(
                        "WxPublicRecyclerView",
                        "firstVisiblePosition----------- = ${firstVisiblePosition}"
                    )
                    if (firstVisiblePosition == 0 && (ev.x > downX)) {
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                Log.e("WxPublicRecyclerView", "ACTION_UP")
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}