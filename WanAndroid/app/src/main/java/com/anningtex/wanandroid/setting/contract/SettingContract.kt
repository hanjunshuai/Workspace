package com.xing.wanandroid.setting.contract

import com.anningtex.wanandroid.base.mvp.IView


interface SettingContract {

    interface View : IView {
        fun onLogoutResult()
    }

    interface Presenter {
        fun logout()
    }
}