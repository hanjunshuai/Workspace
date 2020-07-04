package com.anningtex.wanandroid.main.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.db.bean.User

/**
 *
 * @ClassName:      MainContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 15:07
 */
interface MainContract {
    interface View : IView {
        fun onUserInfo(user: User)
    }

    interface Presenter {
        fun getUserInfo()
    }
}