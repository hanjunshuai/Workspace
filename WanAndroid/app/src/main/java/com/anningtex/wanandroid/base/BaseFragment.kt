package com.anningtex.wanandroid.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *
 * @ClassName:      BaseFragment
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/3 14:47
 */
abstract class BaseFragment : Fragment() {
    private lateinit var mContext: Context

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
        val rootView = inflater.inflate(getLayoutResId(), container, false)
        initView(rootView, savedInstanceState)
        initData()
        return rootView
    }

    abstract fun initData()

    abstract fun initView(rootView: View?, savedInstanceState: Bundle?)

    abstract fun getLayoutResId(): Int

}