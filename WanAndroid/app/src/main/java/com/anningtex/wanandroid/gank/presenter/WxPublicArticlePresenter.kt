package com.anningtex.wanandroid.gank.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.gank.contract.WxPublicArticleContract
import com.anningtex.wanandroid.home.bean.ArticleResponse
import com.anningtex.wanandroid.http.BaseObserver

/**
 *
 * @ClassName:      WxPublicArticlePresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 16:33
 */
class WxPublicArticlePresenter : BasePresenter<WxPublicArticleContract.View>(),
    WxPublicArticleContract.Presenter {
    override fun getWxPublicArticle(id: Int, page: Int) {
        addSubscribe(
            create(ApiService::class.java).getWxPublicArticle(id, page),
            object : BaseObserver<ArticleResponse>() {
                override fun onSuccess(data: ArticleResponse?) {
                    getView()?.onWxPublicArticle(page, data?.datas)
                }
            })
    }
}