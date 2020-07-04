package com.anningtex.wanandroid.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 *
 * @ClassName:      IntentUtils
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 13:32
 */
fun gotoActivity(activity: Activity, clazz: Class<Any>) {
    val intent = Intent(activity, clazz)
    activity.startActivity(intent)
}

fun gotoActivity(activity: Activity, clazz: Class<Any>, bundle: Bundle) {
    val intent = Intent(activity, clazz)
    intent.putExtras(bundle)
    activity.startActivity(intent)
}