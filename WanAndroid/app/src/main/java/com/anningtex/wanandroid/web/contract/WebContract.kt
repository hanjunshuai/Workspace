package com.anningtex.wanandroid.web.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.web.bean.AddFavoriteResponse

/**
 *
 * @ClassName:      WebPresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 8:27
 */
interface WebContract {
    interface View : IView {
        fun onAddFavorited(addFavoriteResponse: AddFavoriteResponse?)
    }

    interface Presenter {
        fun addFavorite(id: Int, title: String, author: String, link: String)
    }
}