package com.anningtex.wanandroid.db

import com.anningtex.wanandroid.WanAndroidApplication

/**
 *
 * @ClassName:      DbManager
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 15:14
 */
class DbManager {

    private var userDao: UserDao

    private constructor() {
        val openHelper = DaoMaster.DevOpenHelper(WanAndroidApplication.getContext(), "user.db")
        val daoMaster = DaoMaster(openHelper.writableDb)
        val daoSession = daoMaster.newSession()
        userDao = daoSession.userDao
    }

    companion object {
        fun getInstance(): DbManager {
            return DbManagerHolder.holder
        }
    }

    private object DbManagerHolder {
        val holder = DbManager()
    }

    fun getUserDao(): UserDao {
        return userDao
    }
}