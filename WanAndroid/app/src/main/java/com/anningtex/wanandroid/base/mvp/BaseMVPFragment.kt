package com.anningtex.wanandroid.base.mvp

import com.anningtex.wanandroid.base.BaseFragment

/**
 *
 * @ClassName:      BaseMVPFragment
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 13:09
 */
abstract class BaseMVPFragment<in V : IView, P : IPresenter<in V>> : BaseFragment(), IView {
    protected lateinit var presenter: P

    override fun initData() {
        presenter = createPresenter()
        presenter.attachView(this as V)
    }

    abstract fun createPresenter(): P
}