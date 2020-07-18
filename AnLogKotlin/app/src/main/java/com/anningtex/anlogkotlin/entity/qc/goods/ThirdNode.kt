package com.anningtex.anlogkotlin.entity.qc.goods

import com.chad.library.adapter.base.entity.node.BaseNode

/**
 * @ClassName: ThirdNode
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/3/27 17:07
 */
class ThirdNode : BaseNode() {
    var orderNo: String? = null
    var orderId: String? = null
    var deliveryNo: String? = null
    var metersPerBale: String? = null
    var qalesQuantity: String? = null
    var notes: String? = null
    var isLast = false

    override val childNode: MutableList<BaseNode>? = null

}