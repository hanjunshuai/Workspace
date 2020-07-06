package com.xing.wanandroid.user.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.db.DbManager
import com.anningtex.wanandroid.db.bean.User
import com.anningtex.wanandroid.http.BaseObserver
import com.anningtex.wanandroid.main.bean.LoggedInEvent
import com.xing.wanandroid.user.contract.LoginContract
import org.greenrobot.eventbus.EventBus

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun login(username: String, password: String) {
        addSubscribe(
            create(ApiService::class.java).login(username, password),
            object : BaseObserver<User>(getView()) {
                override fun onSuccess(user: User?) {
                    getView()?.onLoginResult(username, user)
                    DbManager.getInstance().getUserDao().deleteAll()
                    DbManager.getInstance().getUserDao().insert(user)
                    EventBus.getDefault().post(LoggedInEvent(user))
                }
            })
    }
}