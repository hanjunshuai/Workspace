package com.xing.wanandroid.user.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.db.bean.User
import com.xing.wanandroid.user.bean.LoginResponse

interface LoginContract {

    interface View : IView {
        fun onLoginResult(username: String, user: User?)
    }

    interface Presenter {
        fun login(username: String, password: String)
    }
}
