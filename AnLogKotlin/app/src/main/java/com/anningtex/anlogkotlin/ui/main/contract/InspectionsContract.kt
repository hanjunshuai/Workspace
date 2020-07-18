package com.anningtex.anlogkotlin.ui.main.contract

import com.anningtex.anlogkotlin.base.BaseContract
import com.anningtex.anlogkotlin.entity.qc.QcOrderResponse

/**
 *
 * @ClassName:      InspectionsContract
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 16:05
 */
interface InspectionsContract {
    interface View : BaseContract.View {
        fun setQcOrders(data: MutableList<QcOrderResponse>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getQcOrder()
    }
}