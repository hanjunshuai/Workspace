package com.anningtex.wanandroid.gank.bean

/**
 *
 * @ClassName:      GankToday
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 16:07
 */
data class GankToday(
    val desc: String,
    val publishedAt: String,
    val type: String,
    val url: String,
    val who: String,
    val images: ArrayList<String>?
)