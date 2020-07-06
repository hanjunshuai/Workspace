package com.anningtex.wanandroid.system.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.system.bean.SystemCategory

/**
 *
 * @ClassName:      SystemContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 11:42
 */
interface SystemContract {
    interface View : IView {
        fun onSystemCategory(data: List<SystemCategory>?)
    }

    interface Presenter {
        fun getSystemCategory()
    }
}