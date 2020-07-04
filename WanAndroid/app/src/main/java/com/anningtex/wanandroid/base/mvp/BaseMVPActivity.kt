package com.anningtex.wanandroid.base.mvp

import com.anningtex.wanandroid.base.BaseActivity

/**
 *
 * @ClassName:      BaseMVPActivity
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 11:28
 */
abstract class BaseMVPActivity<in V : IView, P : IPresenter<in V>> : BaseActivity(), IView {
    protected lateinit var presenter: P


    override fun initData() {
        super.initData()
        presenter = createPresenter()
        presenter.attachView(this as V)
    }

    abstract fun createPresenter(): P

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }
}