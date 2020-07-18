package com.anningtex.anlogkotlin.http

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 *
 * @ClassName:      SSLSocketClient
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 11:58
 */
class SSLSocketClient {
    companion object {
        fun getSSLSocketFactory(): SSLSocketFactory {
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, getTrustManager(), SecureRandom())
            return sslContext.socketFactory
        }

        private fun getTrustManager(): Array<TrustManager> {
            return arrayOf(object : X509TrustManager {
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

            })
        }

        fun getHostnameVerifier(): HostnameVerifier {
            return HostnameVerifier { hostname, session -> true }
        }
    }
}