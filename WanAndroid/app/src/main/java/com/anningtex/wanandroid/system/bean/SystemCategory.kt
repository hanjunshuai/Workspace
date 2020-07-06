package com.anningtex.wanandroid.system.bean

/**
 *
 * @ClassName:      SystemCategory
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 11:41
 */
data class SystemCategory(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: ArrayList<SystemCategory>
)