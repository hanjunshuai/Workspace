package com.anningtex.wanandroid.main.presenter

import android.util.Log
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.db.DbManager
import com.anningtex.wanandroid.main.contract.MainContract

/**
 *
 * @ClassName:      MainPresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 15:12
 */
class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {
    override fun getUserInfo() {
        var users = DbManager.getInstance().getUserDao().loadAll()
        Log.e("MainPresenter", "users = $users")
        if (users.size > 0) {
            getView()?.onUserInfo(users[0])
        }
    }
}