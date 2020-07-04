package com.anningtex.wanandroid.util

import android.content.Context
import android.content.pm.PackageManager

/**
 *
 * @ClassName:      ApkVersionUtils
 * @Description:    获取版本号的工具类
 * @Author:         alvis
 * @CreateDate:     2020/7/3 13:57
 */
object ApkVersionUtils {
    /**
     * 获取当前本地apk的版本
     */
    fun getVersionCode(context: Context): Int {
        var versionCode = 0
        try {
            // 获取软件版本号,对应AndroidManifest.xml 下 Android:versionCode
            versionCode = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionCode
    }

    /**
     * 获取版本号名称
     */
    fun getVersionName(context: Context): String {
        var versionName = ""
        try {
            versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionName
    }
}