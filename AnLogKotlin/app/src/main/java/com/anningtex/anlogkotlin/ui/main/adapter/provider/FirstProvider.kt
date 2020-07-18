package com.anningtex.anlogkotlin.ui.main.adapter.provider

import android.view.View
import com.anningtex.anlogkotlin.R
import com.anningtex.anlogkotlin.entity.qc.goods.FirstNode
import com.anningtex.anlogkotlin.listener.OnNodeItemClickListener
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: FirstProvider
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/3/27 17:02
 */
class FirstProvider : BaseNodeProvider() {
    var mOnNodeItemClickListener: OnNodeItemClickListener? = null

    override val itemViewType: Int = 1

    override val layoutId: Int = R.layout.item_node_first

    override fun convert(
        helper: BaseViewHolder,
        data: BaseNode
    ) {
        val entity = data as FirstNode
        helper.setText(R.id.item_tv_delivery_number, entity.deliveryNo)
        helper.setText(R.id.item_tv_plan_time, entity.planDate)
        helper.setText(R.id.item_tv_load_time, entity.loadDate)
    }

    override fun onClick(
        helper: BaseViewHolder,
        view: View,
        data: BaseNode,
        position: Int
    ) {
        mOnNodeItemClickListener?.onItemClick(data, position)
    }
}