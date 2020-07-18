package com.anningtex.anlogkotlin.http

import com.anningtex.anlogkotlin.AnLogApplication
import com.anningtex.anlogkotlin.config.BASE_URL
import com.anningtex.anlogkotlin.http.gson.GsonDConverterFactory
import com.anningtex.anlogkotlin.util.logInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @ClassName:      RetrofitClient
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 10:40
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
            .addInterceptor(HttpLoggingInterceptor())
            .cookieJar(AnLogApplication.getInstance().getPersistentCookieJar())
//            .addInterceptor()
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonDConverterFactory.create())
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