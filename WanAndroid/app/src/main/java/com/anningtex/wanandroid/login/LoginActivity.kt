package com.anningtex.wanandroid.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.mvp.BaseMVPActivity
import com.anningtex.wanandroid.db.bean.User
import com.anningtex.wanandroid.util.gotoActivity
import com.anningtex.wanandroid.widget.ClearEditText
import com.anningtex.wanandroid.widget.LoginView
import com.xing.wanandroid.user.contract.LoginContract
import com.xing.wanandroid.user.presenter.LoginPresenter

class LoginActivity : BaseMVPActivity<LoginContract.View, LoginPresenter>(),
    LoginContract.View, View.OnClickListener {
    private lateinit var usernameEditText: ClearEditText
    private lateinit var passwordEditText: ClearEditText
    private lateinit var loginView: LoginView
    private lateinit var registerTxtView: TextView
    private lateinit var closeImgView: ImageView
    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun createPresenter(): LoginPresenter = LoginPresenter()

    override fun initView() {
        closeImgView = findViewById(R.id.iv_login_close)
        usernameEditText = findViewById(R.id.cet_login_username)
        passwordEditText = findViewById(R.id.cet_login_password)
        loginView = findViewById(R.id.lv_login)
        registerTxtView = findViewById(R.id.tv_user_register)
    }

    override fun initData() {
        super.initData()
        val spannableString = SpannableString(registerTxtView.text.toString().trim())
        spannableString.setSpan(
            UnderlineSpan(),
            0,
            registerTxtView.text.toString().trim().length,
            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
        )
        registerTxtView.text = spannableString
        registerTxtView.setOnClickListener(this)
        loginView.setOnClickListener(this)
        closeImgView.setOnClickListener(this)
    }

    override fun onLoginResult(username: String, user: User?) {
        finishLoginActivity()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.lv_login -> {
                login()
            }
            R.id.tv_user_register -> {
                gotoRegisterActivity()
            }
            R.id.iv_login_close -> {
                finishLoginActivity()
            }
        }
    }

    private fun login() {
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "请输入密码", Toast.LENGTH_LONG).show()
            return
        }
        presenter.login(username, password)
        loginView.setState(LoginView.STATE_LOADING)
    }


    private fun gotoRegisterActivity() {
        gotoActivity(this, RegisterActivity().javaClass)
    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
//        loginView.setState(LoginView.STATE_FAILED)
    }

    /**
     * 关闭 LoginActivity
     */
    private fun finishLoginActivity() {
        finish()
        overridePendingTransition(0, R.anim.anim_bottom_exit)
    }

    override fun onDestroy() {
        super.onDestroy()
        loginView.release()
    }
}
