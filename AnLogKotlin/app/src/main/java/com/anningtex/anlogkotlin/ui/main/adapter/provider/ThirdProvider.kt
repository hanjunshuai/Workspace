package com.anningtex.anlogkotlin.ui.main.adapter.provider

import android.view.View
import com.anningtex.anlogkotlin.R
import com.anningtex.anlogkotlin.entity.qc.goods.ThirdNode
import com.anningtex.anlogkotlin.listener.OnNodeItemClickListener
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: ThirdProvider
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/3/27 17:02
 */
class ThirdProvider : BaseNodeProvider() {
    var mOnNodeItemClickListener: OnNodeItemClickListener? = null

    override val itemViewType: Int = 3

    override val layoutId: Int = R.layout.item_node_third

    override fun convert(
        helper: BaseViewHolder,
        data: BaseNode
    ) {
        val entity = data as ThirdNode
        helper.setText(R.id.item_tv_order_number, entity.orderNo)
        helper.setText(R.id.item_tv_meters_per_bale, entity.metersPerBale)
        helper.setText(R.id.item_tv_bales_quantity, entity.qalesQuantity)
        helper.setText(R.id.item_tv_notes, entity.notes)
        if (entity.notes == null || "" == entity.notes) {
            helper.setGone(R.id.item_tv_notes, true)
        }
        if (entity.isLast) {
            helper.itemView.background =
                helper.itemView.context.getDrawable(R.drawable.container_bg_child)
        }
    }

    override fun onClick(
        helper: BaseViewHolder,
        view: View,
        data: BaseNode,
        position: Int
    ) {
        super.onClick(helper, view, data, position)
        mOnNodeItemClickListener?.onItemClick(data, position)
    }
}