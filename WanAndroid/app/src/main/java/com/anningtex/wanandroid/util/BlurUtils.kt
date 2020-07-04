package com.anningtex.wanandroid.util

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

/**
 *
 * @ClassName:      BlurUtils
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 17:01
 */
/**
 * 0 - 25
 */
fun blur(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
    val rs = RenderScript.create(context)
    val outputBitmap = Bitmap.createBitmap(bitmap)
    val input = Allocation.createFromBitmap(rs, bitmap)
    val output = Allocation.createFromBitmap(rs, bitmap)
    val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
    scriptIntrinsicBlur.setRadius(radius.toFloat())
    scriptIntrinsicBlur.setInput(input)
    scriptIntrinsicBlur.forEach(output)
    output.copyTo(outputBitmap)
    return outputBitmap

}