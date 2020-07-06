package com.anningtex.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.anningtex.wanandroid.R

/**
 *
 * @ClassName:      ItemView
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 17:23
 */
class ItemView : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {

        init(context, attributeSet)
    }
    private fun init(context: Context, attributeSet: AttributeSet?) {
        readAttrs(context, attributeSet)
        LayoutInflater.from(context).inflate(R.layout.layout_item_view, this, true)
    }

    private fun readAttrs(context: Context, attributeSet: AttributeSet?) {
    }

}