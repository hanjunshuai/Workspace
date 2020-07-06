package com.anningtex.wanandroid.meizi.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.xing.wanandroid.meizi.bean.Meizi

/**
 *
 * @ClassName:      MeiziContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 16:50
 */
interface MeiziContract {
    interface View : IView {
        fun onMeiziList(page: Int, list: List<Meizi>?)
    }

    interface Presenter {
        fun getMeiziList(page: Int, pageSize: Int)
    }
}