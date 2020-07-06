package com.anningtex.wanandroid.widget

import android.view.View
import android.view.ViewGroup

/**
 *
 * @ClassName:      ViewMarginWrapper
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 18:01
 */
class ViewMarginWrapper(val view: View) {

    fun setLeftMargin(margin: Int) {
        val lp = view.layoutParams as ViewGroup.MarginLayoutParams
        lp.leftMargin = margin
        view.layoutParams = lp
    }

    fun getLeftMargin(): Int {
        val marginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        return marginLayoutParams.leftMargin
    }

    fun setRightMargin(margin: Int) {
        val lp = view.layoutParams as ViewGroup.MarginLayoutParams
        lp.rightMargin = margin
        view.layoutParams = lp
    }

    fun getRightMargin(): Int {
        val marginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        return marginLayoutParams.rightMargin
    }
}