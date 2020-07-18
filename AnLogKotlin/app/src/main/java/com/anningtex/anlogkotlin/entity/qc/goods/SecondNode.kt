package com.anningtex.anlogkotlin.entity.qc.goods

import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode

/**
 * @ClassName: SecondNode
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/3/27 17:06
 */
class SecondNode(
    val title: String
) : BaseExpandNode() {
    var countryId = 0


    init {
        isExpanded = true
    }

    override var childNode: MutableList<BaseNode>? = null
}