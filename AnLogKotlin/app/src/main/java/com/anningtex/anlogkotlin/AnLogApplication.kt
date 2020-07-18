package com.anningtex.anlogkotlin

import android.app.Application
import android.content.Context
import android.util.Log
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

/**
 *
 * @ClassName:      AnLogApplication
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 10:55
 */
class AnLogApplication : Application() {
    private lateinit var cookieJar: PersistentCookieJar

    companion object {
        private lateinit var context: Context

        private lateinit var instance: AnLogApplication

        fun getContext(): Context {
            return context.applicationContext
        }

        fun getInstance(): AnLogApplication {
            return instance
        }
    }


    override fun onCreate() {
        super.onCreate()
        context = this
        instance = this
        cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
    }

    fun getPersistentCookieJar(): PersistentCookieJar {
        return cookieJar
    }


    /**
     * 初始化云推送通道
     */
    private fun initCloudChannel(applicationContext: Context) {
        PushServiceFactory.init(applicationContext)
        val pushService = PushServiceFactory.getCloudPushService()
        pushService.register(applicationContext, object : CommonCallback {
            override fun onSuccess(response: String) {
                Log.i(
                    "TAG",
                    "init cloudChannel success"
                )
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
                Log.e(
                    "TAG",
                    "init cloudChannel failed -- errOrCode:$errorCode -- errorMessage:$errorMessage"
                )
            }
        })
    }

}