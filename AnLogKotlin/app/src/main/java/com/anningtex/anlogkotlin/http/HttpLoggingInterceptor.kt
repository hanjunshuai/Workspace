package com.anningtex.anlogkotlin.http

import android.util.Log
import com.anningtex.anlogkotlin.util.MyLog
import com.anningtex.baselibrary.util.LogUtil
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @ClassName: HttpLoggingInterceptor
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/1/13 17:58
 */
class HttpLoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //拿到Request对象
        val request = chain.request()
        val t1 = System.nanoTime()
        Log.e(
            "日志拦截器",
            " request  = " + String.format(
                "Sending request %s on %s%n%s",
                request.url(),
                chain.connection(),
                request.headers()
            )
        )

        //拿到Response对象
        val response = chain.proceed(request)
        val responseBody = response.peekBody(1024 * 1024.toLong())
        val string = responseBody.string()
        MyLog.e(
            "\n日志拦截器",
            """url :${request.url()}
 request  = $string"""
        )
        val t2 = System.nanoTime()
        //得出请求网络,到得到结果,中间消耗了多长时间
        Log.e(
            "日志拦截器", "response  " + String.format(
                "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()
            )
        )
        if ("POST" == request.method()) {
            if (request.body() is FormBody) {
                val bodyBuilder = FormBody.Builder()
                val formBody = request.body() as FormBody?
                // 先复制原来的参数
                for (i in 0 until formBody!!.size()) {
                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i))
                    Log.e(
                        "参数",
                        formBody.encodedName(i) + "  " + formBody.encodedValue(i)
                    )
                }
            }
        }
        return response
    }
}