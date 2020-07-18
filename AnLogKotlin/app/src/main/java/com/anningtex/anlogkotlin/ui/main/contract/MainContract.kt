package com.anningtex.anlogkotlin.ui.main.contract

import com.anningtex.anlogkotlin.base.BaseContract

/**
 *
 * @ClassName:      MainContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 9:13
 */
interface MainContract {
    interface View : BaseContract.View
    interface Presenter : BaseContract.Presenter<View>
}