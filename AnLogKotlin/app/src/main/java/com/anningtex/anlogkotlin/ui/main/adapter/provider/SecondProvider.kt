package com.anningtex.anlogkotlin.ui.main.adapter.provider

import android.view.View
import com.anningtex.anlogkotlin.R
import com.anningtex.anlogkotlin.entity.qc.goods.SecondNode
import com.anningtex.anlogkotlin.entity.qc.goods.ThirdNode
import com.anningtex.anlogkotlin.listener.OnItemClickListener
import com.anningtex.baselibrary.util.LogUtil
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: SecondProvider
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/3/27 17:02
 */
class SecondProvider : BaseNodeProvider() {
    private var mOnItemClickListener: OnItemClickListener? = null

    override val itemViewType: Int = 2

    override val layoutId: Int = R.layout.item_node_second

    override fun convert(
        helper: BaseViewHolder,
        data: BaseNode
    ) {
        val entity = data as SecondNode
        helper.setText(R.id.title, entity.title + "(" + entity.childNode!!.size + "个)")
    }

    override fun onClick(
        helper: BaseViewHolder,
        view: View,
        data: BaseNode,
        position: Int
    ) {
        val entity = data as SecondNode
        mOnItemClickListener?.onClickItem(position)
        val thirdNode = entity.childNode!![0] as ThirdNode
        LogUtil.e(entity.title + thirdNode.orderNo)
    }
}