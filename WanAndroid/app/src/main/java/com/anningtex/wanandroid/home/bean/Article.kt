package com.anningtex.wanandroid.home.bean

/**
 *
 * @ClassName:      Article
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 16:49
 */
data class Article(
    val id: Int,
    val title: String,
    val author: String,
    val publishTime: Long,
    val superChapterName: String,
    val envelopePic: String,
    val link: String,
    val desc: String,
    val fresh: Boolean,
    val collect: Boolean,
    val type: Int
)