package com.anningtex.wanandroid.setting

import android.os.Build
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.WanAndroidApplication
import com.anningtex.wanandroid.base.mvp.BaseMVPActivity
import com.anningtex.wanandroid.main.bean.LoggedInEvent
import com.anningtex.wanandroid.util.dp2px
import com.anningtex.wanandroid.util.isCookieNotEmpty
import com.xing.wanandroid.setting.contract.SettingContract
import com.xing.wanandroid.setting.presenter.SettingPresenter
import org.greenrobot.eventbus.EventBus

class SettingActivity : BaseMVPActivity<SettingContract.View, SettingPresenter>(),
    SettingContract.View {

    private lateinit var logoutBtn: Button
    private lateinit var toolbar: Toolbar

    override fun getLayoutResId(): Int {
        return R.layout.activity_setting
    }

    override fun createPresenter(): SettingPresenter = SettingPresenter()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        toolbar = findViewById(R.id.tb_setting)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "设置"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = dp2px(mContext, 5f)
        toolbar.setNavigationOnClickListener { finish() }
        logoutBtn = findViewById(R.id.btn_logout)
        logoutBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                presenter.logout()
            }
        })
    }

    override fun initData() {
        super.initData()
        val loggedIn = isCookieNotEmpty(mContext)
        if (loggedIn) {
            logoutBtn.visibility = View.VISIBLE
        } else {
            logoutBtn.visibility = View.GONE
        }
    }

    override fun onLogoutResult() {
        WanAndroidApplication.getInstance().getPersistentCookieJar().clear()
        EventBus.getDefault().post(LoggedInEvent(null))
        finish()
    }
}
