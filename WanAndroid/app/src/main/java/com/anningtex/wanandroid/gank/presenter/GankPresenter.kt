package com.anningtex.wanandroid.gank.presenter

import android.util.Log
import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.gank.bean.GankToday
import com.anningtex.wanandroid.gank.bean.WxPublic
import com.anningtex.wanandroid.gank.contract.GankContract
import com.anningtex.wanandroid.http.BaseObserver

/**
 *
 * @ClassName:      GankPresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 16:04
 */
class GankPresenter : BasePresenter<GankContract.View>(), GankContract.Presenter {
    override fun getGankToday() {
        addSubscribe(create(ApiService::class.java).getGankToday(), object : BaseObserver<HashMap<String, List<GankToday>>>() {
            override fun onSuccess(map: HashMap<String, List<GankToday>>?) {
                Log.e("debug", "map = $map")
                getView()?.onGankToday(map)
            }
        })
    }

    override fun getWxPublic() {
        addSubscribe(create(ApiService::class.java).getWxPublic(), object : BaseObserver<List<WxPublic>>() {
            override fun onSuccess(data: List<WxPublic>?) {
                getView()?.onWxPublic(data)
            }
        })
    }
}