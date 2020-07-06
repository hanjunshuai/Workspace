package com.anningtex.wanandroid.gank.contract

import com.anningtex.wanandroid.base.mvp.IView
import com.anningtex.wanandroid.gank.bean.GankToday
import com.anningtex.wanandroid.gank.bean.WxPublic

/**
 *
 * @ClassName:      GankContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 16:03
 */
interface GankContract {
    interface View : IView {
        fun onWxPublic(list: List<WxPublic>?)

        fun onGankToday(map: HashMap<String, List<GankToday>>?)
    }

    interface Presenter {
        fun getWxPublic()
        fun getGankToday()
    }
}