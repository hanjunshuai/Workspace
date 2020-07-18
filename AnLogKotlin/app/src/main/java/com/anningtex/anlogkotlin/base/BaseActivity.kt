package com.anningtex.anlogkotlin.base

import android.app.ProgressDialog
import com.anningtex.baselibrary.base.AbsBaseActivity
import com.anningtex.baselibrary.base.AbsBasePresenter
import com.anningtex.baselibrary.util.LogUtil

/**
 * @ClassName: BaseActivity
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/16 9:02
 */
abstract class BaseActivity<V : BaseContract.View, P : AbsBasePresenter<V>> :
    AbsBaseActivity<V, P>(),
    BaseContract.View {
    var progressBar: ProgressDialog? = null

    override fun initViews() {
        progressBar = ProgressDialog(this)
    }

    override fun showLoading() {
        if (progressBar == null) {
            LogUtil.e("loadingView is null")
            return
        }
        progressBar?.show()
    }

    override fun dismissLoading() {
        if (progressBar!!.isShowing)
            progressBar?.dismiss()
    }
}