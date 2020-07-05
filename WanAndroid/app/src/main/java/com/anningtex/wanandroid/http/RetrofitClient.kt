package com.anningtex.wanandroid.http

import com.anningtex.wanandroid.WanAndroidApplication
import com.anningtex.wanandroid.util.logInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @ClassName:      RetrofitClient
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 11:56
 */
class RetrofitClient {
    var okHttpClient: OkHttpClient
    var retrofit: Retrofit

    private constructor() {
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            .addInterceptor(logInterceptor())
            .cookieJar(WanAndroidApplication.getInstance().getPersistentCookieJar())
//            .addInterceptor()
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        private var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }

        @Synchronized
        fun get(): RetrofitClient {
            return instance!!
        }
    }
}