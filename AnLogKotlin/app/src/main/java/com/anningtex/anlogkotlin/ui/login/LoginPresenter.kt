package com.anningtex.anlogkotlin.ui.login

import com.anningtex.anlogkotlin.api.ApiServices
import com.anningtex.anlogkotlin.base.BasePresenter
import com.anningtex.anlogkotlin.config.FOREIGN
import com.anningtex.anlogkotlin.config.QC
import com.anningtex.anlogkotlin.config.SUPER_ADMIN
import com.anningtex.anlogkotlin.config.WORE_HOSE
import com.anningtex.anlogkotlin.entity.LoginResponse
import com.anningtex.anlogkotlin.http.BaseObserver
import com.anningtex.anlogkotlin.util.MD5Util
import com.anningtex.baselibrary.util.LogUtil

/**
 *
 * @ClassName:      LoginPresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 9:53
 */
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun login(username: String, password: String, deviceId: String) {
        addSubscribe(create(ApiServices::class.java).login(
            username,
            MD5Util.encode(password),
            "52fc173a5f0243b6861bb45ab29d5b24"
        ),
            object : BaseObserver<LoginResponse>(this.attachView) {
                override fun onSuccess(code: Int, msg: String, data: LoginResponse) {
                    attachView?.toast(msg)
                    LogUtil.e("${data.menu?.module?.size}")
                    LogUtil.e("${data.menu?.module?.get(0)}")
                    LogUtil.e("${data.menu?.urlList?.toString()}")
                    attachView?.dismissLoading()
                    when (data?.role) {
                        QC -> {
                            attachView?.toWoreHose()
                        }
                        WORE_HOSE -> {
                        }
                        FOREIGN -> attachView?.toast(FOREIGN)
                        SUPER_ADMIN -> attachView?.toast(SUPER_ADMIN)
                    }
                }
            })
    }
}