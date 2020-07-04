package com.anningtex.wanandroid.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.common.annotation.EventBusSubscribe
import com.anningtex.wanandroid.util.EventBusUtils
import com.jaeger.library.StatusBarUtil

/**
 * @ClassName: BaseActivity
 * @Description: BaseActivity 基类activity
 * @Author: alvis
 * @CreateDate: 2020/7/3 13:45
 */
abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        mContext = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 黑色
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        StatusBarUtil.setColor(this, resources.getColor(R.color.colorPrimary), 0)
        if (isEventBusAnnotationPresent()) {
            EventBusUtils.register(this)
        }

        initView()
        initData()

    }

    abstract fun initView()
    open fun initData() {}
    protected abstract fun getLayoutResId(): Int

    private fun isEventBusAnnotationPresent(): Boolean {
        return javaClass.isAnnotationPresent(EventBusSubscribe::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isEventBusAnnotationPresent()) {
            EventBusUtils.unregister(this)
        }
    }
}