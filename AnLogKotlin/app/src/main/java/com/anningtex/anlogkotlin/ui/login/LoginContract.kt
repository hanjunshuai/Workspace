package com.anningtex.anlogkotlin.ui.login

import com.anningtex.anlogkotlin.base.BaseContract

/**
 *
 * @ClassName:      LoginContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 9:52
 */
interface LoginContract {
    interface View : BaseContract.View {
        fun getUsername(): String
        fun getPassword(): String
        fun toWoreHose()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun login(username: String, password: String, deviceId: String)
    }
}