package com.xing.wanandroid.favorite.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.home.bean.ArticleResponse


interface FavoriteContract {

    interface View : IView {
        fun onArticleFavorite(page: Int, response: ArticleResponse?)
    }

    interface Presenter {
        fun getArticleFavorites(page: Int)
    }
}