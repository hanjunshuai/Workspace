package com.anningtex.anlogkotlin.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anningtex.baselibrary.base.AbsBaseFragment
import com.anningtex.baselibrary.base.AbsBasePresenter

/**
 *
 * @ClassName:      BaseFragment
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 9:36
 */
abstract class BaseFragment<V : BaseContract.View, P : AbsBasePresenter<V>> :
    Fragment(),
    BaseContract.View {

    private lateinit var mContext: Context
    lateinit var mPresenter: P
    private lateinit var progressBar: ProgressDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(layoutResId, container, false)
        initView(rootView, savedInstanceState)
        return rootView
    }

    abstract fun createPresenter(): P

    open fun initView(rootView: View?, savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        mPresenter.attachView(this as V)
        progressBar = ProgressDialog(context)
    }

    protected abstract val layoutResId: Int

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
    }

    fun startAct(cls: Class<*>?) {
        val intent = Intent(context, cls)
        startActivity(intent)
    }

    fun startAct(cls: Class<*>?, bundle: Bundle?) {
        val intent = Intent(context, cls)
        intent.putExtras(bundle!!)
        startActivity(intent)
    }

    fun startAct(cls: Class<*>?, name: String?, value: String?) {
        val intent = Intent(activity, cls)
        intent.putExtra(name, value)
        startActivity(intent)
    }

    override fun toast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun toast(msg: Int) {
        Toast.makeText(mContext, getString(msg), Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar!!.show()
    }

    override fun dismissLoading() {
        if (progressBar?.isShowing!!)
            progressBar?.dismiss()
    }
}