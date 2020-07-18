package com.anningtex.anlogkotlin.entity.qc.goods

import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode

/**
 *
 * @ClassName:      FirstNode
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/18 14:45
 */
class FirstNode : BaseExpandNode() {

    var deliveryNo: String? = null
    var planDate: kotlin.String? = null
    var loadDate: kotlin.String? = null
    override var childNode: MutableList<BaseNode>? = null

    init {
        isExpanded = true
    }

}