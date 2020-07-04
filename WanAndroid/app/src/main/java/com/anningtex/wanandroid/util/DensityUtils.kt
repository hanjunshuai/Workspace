package com.anningtex.wanandroid.util

import android.content.Context
import android.util.TypedValue

/**
 *
 * @ClassName:      DensityUtils
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 17:04
 */
fun dp2px(context: Context, dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    )
}