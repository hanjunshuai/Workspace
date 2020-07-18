package com.anningtex.anlogkotlin.ui.splash

import com.anningtex.anlogkotlin.base.BaseContract

/**
 *
 * @ClassName:      SplashContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 9:20
 */
interface SplashContract {
    interface View : BaseContract.View {}
    interface Presenter : BaseContract.Presenter<View> {}
}