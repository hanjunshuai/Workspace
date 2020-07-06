package com.anningtex.wanandroid.system.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.home.bean.Article

/**
 *
 * @ClassName:      SystemArticleContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 13:34
 */
interface SystemArticleContract {
    interface View : IView {
        fun onSystemArticles(page: Int, list: List<Article>?)
    }

    interface Presenter {
        fun getSystemArticles(page: Int, cid: Int)
    }
}
