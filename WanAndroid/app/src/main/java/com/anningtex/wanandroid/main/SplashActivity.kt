package com.anningtex.wanandroid.main

import android.animation.Animator
import com.airbnb.lottie.LottieAnimationView
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.base.BaseActivity
import com.anningtex.wanandroid.util.gotoActivity

class SplashActivity : BaseActivity() {
    private lateinit var logoLottieView: LottieAnimationView

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun initView() {
        logoLottieView = findViewById(R.id.lav_logo)

        logoLottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                gotoMainActivity()
            }
        })
    }

    private fun gotoMainActivity() {
        gotoActivity(this, MainActivity().javaClass)
        finish()
    }
}
