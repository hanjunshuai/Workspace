package com.anningtex.wanandroid.home.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.home.bean.Banner

/**
 *
 * @ClassName:      HomeContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 16:37
 */
interface HomeContract {
    interface View : IView {
        fun onBanner(data: List<Banner>?)
        fun onArticles(page: Int, data: List<Article>?)
    }

    interface Presenter {}
}