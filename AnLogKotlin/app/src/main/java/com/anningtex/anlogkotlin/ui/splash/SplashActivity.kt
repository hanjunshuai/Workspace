package com.anningtex.anlogkotlin.ui.splash

import android.os.Handler
import android.os.Message
import android.widget.Button
import com.anningtex.anlogkotlin.R
import com.anningtex.anlogkotlin.base.BaseActivity
import com.anningtex.anlogkotlin.ui.login.LoginActivity

class SplashActivity : BaseActivity<SplashContract.View, SplashPresenter>(), SplashContract.View {
    private var count: Int = 3
    lateinit var btnSkip: Button;
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    btnSkip.setText("跳过 (" + getCount() + ")")
                }
            }
        }
    }

    fun getCount(): Int {
        count--;
        if (count == 0) {
            startAct(LoginActivity::class.java)
            onBackPressed()
        }
        handler.sendEmptyMessageDelayed(0, 1000)
        return if (count <= 0) 0 else count;
    }

    override val layoutResId: Int = R.layout.activity_splash

    override fun createPresenter(): SplashPresenter = SplashPresenter()

    override fun initViews() {
        btnSkip = findViewById(R.id.btn_skip)
        btnSkip.setOnClickListener {
            startAct(LoginActivity::class.java)
            handler.removeMessages(0)
            onBackPressed()
        }
        handler.sendEmptyMessageDelayed(0, 1000)
    }
}
