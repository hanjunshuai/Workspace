package com.anningtex.wanandroid.gank.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.home.bean.Article

/**
 *
 * @ClassName:      WxPublicArticleContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 16:32
 */
interface WxPublicArticleContract {
    interface View : IView {
        fun onWxPublicArticle(page: Int, list: List<Article>?)
    }

    interface Presenter {
        fun getWxPublicArticle(id: Int, mCurPage: Int)
    }
}