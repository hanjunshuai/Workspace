package com.anningtex.wanandroid.util

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @ClassName:      DateUtil
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 16:43
 */
fun format(time: Long): String {
    var date = Date(time)
    var sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(date)
}