package com.anningtex.anlogkotlin.ui.main.presenter

import com.anningtex.anlogkotlin.api.ApiServices
import com.anningtex.anlogkotlin.base.BasePresenter
import com.anningtex.anlogkotlin.entity.qc.QcOrderResponse
import com.anningtex.anlogkotlin.http.BaseObserver
import com.anningtex.anlogkotlin.ui.main.contract.InspectionsContract
import com.anningtex.baselibrary.util.LogUtil

/**
 *
 * @ClassName:      InspectionsPresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 16:06
 */
class InspectionsPresenter : BasePresenter<InspectionsContract.View>(),
    InspectionsContract.Presenter {
    override fun getQcOrder() {
        addSubscribe(create(ApiServices::class.java).getQcOrder(), object :
            BaseObserver<MutableList<QcOrderResponse>>(attachView) {
            override fun onSuccess(code: Int, msg: String, data: MutableList<QcOrderResponse>) {
                attachView?.setQcOrders(data)
            }
        })
    }
}