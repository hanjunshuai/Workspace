package com.anningtex.anlogkotlin.ui.login

import android.widget.TextView
import com.anningtex.anlogkotlin.R
import com.anningtex.anlogkotlin.base.BaseActivity
import com.anningtex.anlogkotlin.ui.main.act.MainActivity
import com.anningtex.anlogkotlin.weight.ClearEditText

class LoginActivity : BaseActivity<LoginContract.View, LoginPresenter>(), LoginContract.View {
    lateinit var btnLogin: TextView
    lateinit var tvUserName: ClearEditText
    lateinit var tvPassword: ClearEditText
    override val layoutResId: Int
        get() = R.layout.activity_login

    override fun createPresenter(): LoginPresenter = LoginPresenter()
    override fun initViews() {
        super.initViews()
        tvUserName = findViewById(R.id.login_edit_username)
        tvPassword = findViewById(R.id.login_edit_password)
        btnLogin = findViewById(R.id.login_tv_login)
        btnLogin.setOnClickListener {
            mPresenter?.login(getUsername(), getPassword(), "")
        }
    }

    override fun getUsername(): String = tvUserName.text.toString()
    override fun getPassword(): String = tvPassword.text.toString()
    override fun toWoreHose() {
        startAct(MainActivity::class.java)
        onBackPressed()
    }

}
