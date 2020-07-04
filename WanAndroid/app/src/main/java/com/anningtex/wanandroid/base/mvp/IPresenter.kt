package com.anningtex.wanandroid.base.mvp

/**
 *
 * @ClassName:      IPresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 11:29
 */
interface IPresenter<V : IView> {
    fun attachView(view: V)

    fun detachView()

    fun isViewAttached(): Boolean

    fun getView(): V?
}