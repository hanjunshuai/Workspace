package com.anningtex.wanandroid.login

import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.mvp.BaseMVPActivity
import com.anningtex.wanandroid.widget.ClearEditText
import com.xing.wanandroid.user.bean.RegisterResponse
import com.xing.wanandroid.user.contract.RegisterContract
import com.xing.wanandroid.user.presenter.RegisterPresenter

class RegisterActivity : BaseMVPActivity<RegisterContract.View, RegisterPresenter>(),
    RegisterContract.View, View.OnClickListener {


    private lateinit var toolbar: Toolbar
    private lateinit var usernameEditText: ClearEditText
    private lateinit var passwordEditText: ClearEditText
    private lateinit var repasswordEditText: ClearEditText
    private lateinit var registerBtn: Button

    override fun getLayoutResId(): Int = R.layout.activity_register

    override fun createPresenter(): RegisterPresenter = RegisterPresenter()

    override fun initView() {
        toolbar = findViewById(R.id.tb_register)
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 10f
        supportActionBar?.setTitle(R.string.register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
        usernameEditText = findViewById(R.id.cet_register_username)
        passwordEditText = findViewById(R.id.cet_register_password)
        repasswordEditText = findViewById(R.id.cet_register_repassword)
        registerBtn = findViewById(R.id.btn_register)
    }

    override fun initData() {
        super.initData()
        registerBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                register()
            }
        }
    }

    fun register() {
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val repassword = repasswordEditText.text.toString().trim()
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "请输入密码", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(repassword)) {
            Toast.makeText(mContext, "请再次输入密码", Toast.LENGTH_LONG).show()
            return
        }
        if (!TextUtils.equals(password, repassword)) {
            Toast.makeText(mContext, "两次密码不一致", Toast.LENGTH_LONG).show()
            return
        }
        presenter.register(username, password, repassword)
    }

    override fun onRegisterResult(result: RegisterResponse?) {
        Toast.makeText(mContext, "注册成功", Toast.LENGTH_LONG).show()
        finish()
    }
}
