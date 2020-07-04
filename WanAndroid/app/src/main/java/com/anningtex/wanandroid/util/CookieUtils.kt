package com.anningtex.wanandroid.util

import android.content.Context
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

/**
 *
 * @ClassName:      CookieUtils
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 16:06
 */
/**
 * 判断 Cookie 是否为空
 */
fun isCookieNotEmpty(context: Context): Boolean {
    val spp = SharedPrefsCookiePersistor(context)
    val cookiesList = spp.loadAll()
    if (cookiesList.isEmpty()) {
        return false
    }
    return true
}