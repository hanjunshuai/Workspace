package com.anningtex.wanandroid

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.*
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader

/**
 *
 * @ClassName:      WanAndroidApplication
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/3 13:43
 */
class WanAndroidApplication : Application() {
    private lateinit var cookieJar: PersistentCookieJar

    init {
        // 设置全局的 Header 构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
            override fun createRefreshHeader(
                context: Context,
                layout: RefreshLayout
            ): RefreshHeader {
                //指定为经典Header，默认是 贝塞尔雷达 Header
                return ClassicsHeader(Companion.context)
//                    .setPrimaryColor(ContextCompat.getColor(Companion.context, R.color.colorAccent))  // header 背景
                    .setAccentColor(
                        ContextCompat.getColor(
                            Companion.context,
                            R.color.black_f222
                        )
                    ) // header 中文字，icon 颜色
            }
        })

        // 设置全局的 footer 构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
            override fun createRefreshFooter(
                context: Context,
                layout: RefreshLayout
            ): RefreshFooter {
                return ClassicsFooter(Companion.context)
                    .setAccentColor(
                        ContextCompat.getColor(
                            Companion.context,
                            R.color.black_f222
                        )
                    )
            }
        })
    }

    companion object {
        private lateinit var context: Context

        private lateinit var instance: WanAndroidApplication

        fun getContext(): Context {
            return context.applicationContext
        }

        fun getInstance(): WanAndroidApplication {
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

}