package com.anningtex.anlogkotlin.ui.main.contract

import com.anningtex.anlogkotlin.base.BaseContract
import com.anningtex.anlogkotlin.entity.qc.DeliveryGoodsResponse

/**
 *
 * @ClassName:      DeliveryGoodsContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 16:18
 */
interface DeliveryGoodsContract {
    interface View : BaseContract.View {
        fun setDeliveryGoods(data: MutableList<DeliveryGoodsResponse>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getDeliveryGoods()
    }
}