package com.anningtex.anlogkotlin.ui.main.adapter

import com.anningtex.anlogkotlin.entity.qc.goods.FirstNode
import com.anningtex.anlogkotlin.entity.qc.goods.SecondNode
import com.anningtex.anlogkotlin.entity.qc.goods.ThirdNode
import com.anningtex.anlogkotlin.ui.main.adapter.provider.FirstProvider
import com.anningtex.anlogkotlin.ui.main.adapter.provider.SecondProvider
import com.anningtex.anlogkotlin.ui.main.adapter.provider.ThirdProvider
import com.chad.library.adapter.base.BaseNodeAdapter
import com.chad.library.adapter.base.entity.node.BaseNode as BaseNode1

/**
 *
 * @ClassName:      DeliveryGoodsAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/18 14:42
 */
class DeliveryGoodsAdapter : BaseNodeAdapter() {
    var mSecondProvider: SecondProvider? = null
    var mThirdProvider: ThirdProvider? = null
    var mFirstProvider: FirstProvider? = null


    init {
        mFirstProvider = FirstProvider()
        mSecondProvider = SecondProvider()
        mThirdProvider = ThirdProvider()
        mFirstProvider?.let { addNodeProvider(it) }
        mSecondProvider?.let { addNodeProvider(it) }
        mThirdProvider?.let { addNodeProvider(it) }
    }

//    constructor() : super() {
//
//    }


    override fun getItemType(data: List<BaseNode1>, position: Int): Int {
        var node = data[position]
        return when (node) {
            is FirstNode -> {
                1
            }
            is SecondNode -> {
                2
            }
            is ThirdNode -> {
                3
            }
            else -> 1
        }
    }

}