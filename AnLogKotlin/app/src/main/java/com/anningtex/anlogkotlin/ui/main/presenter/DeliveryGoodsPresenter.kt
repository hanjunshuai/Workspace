package com.anningtex.anlogkotlin.ui.main.presenter

import com.anningtex.anlogkotlin.api.ApiServices
import com.anningtex.anlogkotlin.base.BasePresenter
import com.anningtex.anlogkotlin.entity.qc.DeliveryGoodsResponse
import com.anningtex.anlogkotlin.http.BaseObserver
import com.anningtex.anlogkotlin.ui.main.contract.DeliveryGoodsContract

/**
 *
 * @ClassName:      DeliveryGoodsPresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 16:19
 */
class DeliveryGoodsPresenter : BasePresenter<DeliveryGoodsContract.View>(),
    DeliveryGoodsContract.Presenter {
    override fun getDeliveryGoods() {
        addSubscribe(create(ApiServices::class.java).getDeliveryGoodsList(), object :
            BaseObserver<MutableList<DeliveryGoodsResponse>>(attachView) {

            override fun onSuccess(
                code: Int,
                msg: String,
                data: MutableList<DeliveryGoodsResponse>
            ) {

                attachView?.setDeliveryGoods(data)
            }

        })
    }
}