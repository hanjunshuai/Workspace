package com.anningtex.wanandroid.system.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.home.bean.ArticleResponse
import com.anningtex.wanandroid.http.BaseObserver
import com.anningtex.wanandroid.system.contract.SystemArticleContract

/**
 *
 * @ClassName:      SystemArticlePresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 13:35
 */
class SystemArticlePresenter : BasePresenter<SystemArticleContract.View>(),
    SystemArticleContract.Presenter {

    override fun getSystemArticles(page: Int, cid: Int) {
        addSubscribe(create(ApiService::class.java).getSystemArticles(page, cid),
            object : BaseObserver<ArticleResponse>() {
                override fun onSuccess(response: ArticleResponse?) {
                    getView()?.onSystemArticles(page, response?.datas)
                }
            })
    }

}