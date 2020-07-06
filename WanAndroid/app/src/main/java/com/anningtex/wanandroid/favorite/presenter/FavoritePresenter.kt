package com.xing.wanandroid.favorite.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.home.bean.ArticleResponse
import com.anningtex.wanandroid.http.BaseObserver
import com.xing.wanandroid.favorite.contract.FavoriteContract

class FavoritePresenter : BasePresenter<FavoriteContract.View>(), FavoriteContract.Presenter {

    override fun getArticleFavorites(page: Int) {
        addSubscribe(create(ApiService::class.java).getArticleFavorites(page), object : BaseObserver<ArticleResponse>() {
            override fun onSuccess(response: ArticleResponse?) {
                getView()?.onArticleFavorite(page, response)
            }
        })
    }
}