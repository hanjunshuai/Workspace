package com.anningtex.wanandroid.meizi.presenter

import android.util.Log
import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.http.BaseObserver
import com.anningtex.wanandroid.meizi.contract.MeiziContract
import com.xing.wanandroid.meizi.bean.Meizi

/**
 * @ClassName: MeiziPresenter
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/6 16:50
 */
class MeiziPresenter : BasePresenter<MeiziContract.View>(), MeiziContract.Presenter{
    override fun getMeiziList(page: Int, pageSize: Int) {
        addSubscribe(
            create(ApiService::class.java).getMeiziList(pageSize, page),
            object : BaseObserver<List<Meizi>>() {
                override fun onSuccess(data: List<Meizi>?) {
                    Log.e("debug", "data = ${data?.size}")
                    if (this@MeiziPresenter.isViewAttached()) {
                        this@MeiziPresenter.getView()?.onMeiziList(page, data)
                    }
                }
            })
    }
}