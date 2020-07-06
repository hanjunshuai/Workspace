package com.anningtex.wanandroid.project.bean

/**
 *
 * @ClassName:      Project
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 10:37
 */
data class Project(
    var id: Int,
    var collect: Boolean,
    var author: String,
    var desc: String,
    var envelopePic: String,
    var fresh: Boolean,
    var type: Int,
    var link: String,
    var niceDate: String,
    var title: String
)