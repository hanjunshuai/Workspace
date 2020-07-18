package com.anningtex.anlogkotlin.listener

import com.chad.library.adapter.base.entity.node.BaseNode

/**
 *
 * @ClassName:      OnItemClickListener
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/18 14:00
 */
interface OnItemClickListener {
    fun onClickItem(position: Int)
}

interface OnNodeItemClickListener {
    fun onItemClick(
        data: BaseNode?,
        position: Int
    )
}