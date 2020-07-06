package com.xing.wanandroid.setting.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.http.BaseObserver
import com.xing.wanandroid.setting.bean.LogoutResult
import com.xing.wanandroid.setting.contract.SettingContract

class SettingPresenter : BasePresenter<SettingContract.View>(), SettingContract.Presenter {

    override fun logout() {
        addSubscribe(
            create(ApiService::class.java).logout(),
            object : BaseObserver<LogoutResult>() {
                override fun onSuccess(result: LogoutResult?) {
                    getView()?.onLogoutResult()
                }
            })
    }
}