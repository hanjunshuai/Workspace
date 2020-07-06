package com.anningtex.wanandroid.system.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.http.BaseObserver
import com.anningtex.wanandroid.system.bean.SystemCategory
import com.anningtex.wanandroid.system.contract.SystemContract

/**
 *
 * @ClassName:      SystemPresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 11:42
 */
class SystemPresenter : BasePresenter<SystemContract.View>(), SystemContract.Presenter {
    override fun getSystemCategory() {
        addSubscribe(create(ApiService::class.java).getSystem(), object : BaseObserver<List<SystemCategory>>() {
            override fun onSuccess(data: List<SystemCategory>?) {
                getView()?.onSystemCategory(data)
            }
        })
    }
}