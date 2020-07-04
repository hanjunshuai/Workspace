package com.anningtex.wanandroid.db.bean

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id

/**
 *
 * @ClassName:      KUser
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 15:10
 */
@Entity
data class KUser(
    @Id
    val sid: Long,
    val admin: Boolean,
    val collectionIds: ArrayList<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)