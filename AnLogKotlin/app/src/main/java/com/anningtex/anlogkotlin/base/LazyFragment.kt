package com.anningtex.anlogkotlin.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anningtex.baselibrary.base.AbsBasePresenter
import com.anningtex.baselibrary.util.LogUtil

/**
 *
 * @ClassName:      BaseLazyFragment
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 15:37
 */

abstract class LazyFragment<V : BaseContract.View, P : AbsBasePresenter<V>> :
    Fragment(),
    BaseContract.View {
    //abstract class BaseLazyFragment<V : BaseContract.View, P : AbsBasePresenter<V>> :
//    BaseFragment<V, P>(),
//    BaseContract.View {
    @JvmField
    protected var mPresenter: P? = null
    private var rootView: View? = null

    //当前Fragment是否首次可见，默认是首次可见
    private var mIsFirstVisible: Boolean = true

    //当前Fragment的View是否已经创建
    private var isViewCreated: Boolean = false

    //当前Fragment的可见状态，一种当前可见，一种当前不可见
    private var currentVisibleState: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(layoutResId, container, false)
        }
        isViewCreated = true
        onFragmentViewCreated(rootView!!)
        return rootView
    }

    abstract fun createPresenter(): P?

    open fun onFragmentViewCreated(view: View?) {
        if (mPresenter == null)
            createPresenter()
        mPresenter?.attachView(this as V)
    }

    protected abstract val layoutResId: Int


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isViewCreated) {
            if (isVisibleToUser && !currentVisibleState) {
                //Fragment可见且状态不是可见(从一个Fragment切换到另外一个Fragment,后一个设置状态为可见)
                disPatchFragment(true)
            } else if (!isVisibleToUser and currentVisibleState) {
                //Fragment不可见且状态是可见(从一个Fragment切换到另外一个Fragment,前一个更改状态为不可见)
                disPatchFragment(false)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!isHidden && userVisibleHint) {
            disPatchFragment(true)
        }
    }

    /**
     *@param visible Fragment当前是否可见，然后调用相关方法
     */
    private fun disPatchFragment(visible: Boolean) {
        //如果父Fragment不可见,则不向下分发给子Fragment
        if (visible && isParentFragmentVisible()) return
        // 如果当前的 Fragment 要分发的状态与 currentVisibleState 相同(都为false)我们就没有必要去做分发了。
        if (currentVisibleState == visible) return
        currentVisibleState = visible
        if (visible) {
            //Fragment可见
            if (mIsFirstVisible) {
                //可见又是第一次
                //改变首次可见的状态
                mIsFirstVisible = false
                onFragmentFirst()
            }
            //可见但不是第一次
            onFragmentVisible()
            //可见状态的时候内层 fragment 生命周期晚于外层 所以在 onFragmentResume 后分发
            dispatchChildFragmentVisibleState(true)
        } else {
            //不可见
            onFragmentInVisible()
            dispatchChildFragmentVisibleState(false)
        }
    }

    /**
     * 重新分发给子Fragment
     */
    private fun dispatchChildFragmentVisibleState(visible: Boolean) {
        val childFragmentManager = childFragmentManager
        val fragments = childFragmentManager.fragments
        if (fragments != null) {
            if (fragments.isNotEmpty()) {
                for (child in fragments) {
                    if (child is LazyFragment<*, *> && !child.isHidden && child.userVisibleHint) {
                        (child as LazyFragment<V, P>).disPatchFragment(
                            visible
                        )
                    }
                }
            }
        }
    }

    open fun onFragmentInVisible() {
        LogUtil.e(javaClass.simpleName, "不可见")
    }

    open fun onFragmentVisible() {
        //子Fragment调用次方法，执行可见操作
        LogUtil.e(javaClass.simpleName, "可见")
    }

    open fun onFragmentFirst() {
        LogUtil.e(javaClass.simpleName, "首次可见")
    }

    /**
     *判断多层嵌套的父fragment是否显示
     */
    private fun isParentFragmentVisible(): Boolean {
        var fragment =
            parentFragment as LazyFragment<V, P>?
        return fragment != null && !fragment?.getCurrentVisibleState()
    }

    fun getCurrentVisibleState(): Boolean {
        return currentVisibleState
    }

    override fun onResume() {
        super.onResume()
        if (!mIsFirstVisible) {
            //表示点击home键又返回操作,设置可见状态为 ture
            if (!isHidden && !userVisibleHint && currentVisibleState) {
                disPatchFragment(true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        //表示点击home键,原来可见的Fragment要走该方法，更改Fragment的状态为不可见
        if (!isHidden && userVisibleHint) {
            disPatchFragment(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //当 View 被销毁的时候我们需要重新设置 isViewCreated mIsFirstVisible 的状态
        isViewCreated = false
        mIsFirstVisible = true
        mPresenter?.detachView()
    }


    override fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun toast(msg: Int) {
        Toast.makeText(context, getString(msg), Toast.LENGTH_SHORT).show()
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

    override fun showLoading() {}

    override fun dismissLoading() {}

}