package com.anningtex.wanandroid.util

import org.greenrobot.eventbus.EventBus

/**
 *
 * @ClassName:      EventBusUtils
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 11:20
 */
class EventBusUtils {
    companion object {
        fun register(obj: Any) {
            EventBus.getDefault().register(obj)
        }

        fun unregister(obj: Any) {
            EventBus.getDefault().unregister(obj)
        }

        fun post(obj: Any) {
            EventBus.getDefault().post(obj)
        }
    }
}