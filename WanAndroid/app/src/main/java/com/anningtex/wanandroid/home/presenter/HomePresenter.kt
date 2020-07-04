package com.anningtex.wanandroid.home.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.home.bean.Banner
import com.anningtex.wanandroid.home.contract.HomeContract
import com.anningtex.wanandroid.http.BaseObserver

/**
 *
 * @ClassName:      HomePresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 16:38
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    private var dataList = ArrayList<Article>()
    fun getBanner() {
        addSubscribe(
            create(ApiService::class.java).getBanner(),
            object : BaseObserver<List<Banner>>() {
                override fun onSuccess(data: List<Banner>?) {
                    getView()?.onBanner(data)
                }
            })
    }
}