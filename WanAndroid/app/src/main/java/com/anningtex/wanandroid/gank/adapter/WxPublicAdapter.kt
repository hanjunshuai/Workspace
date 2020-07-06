package com.anningtex.wanandroid.gank.adapter

import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.gank.bean.WxPublic
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * @ClassName:      WxPublicAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 16:11
 */
class WxPublicAdapter : BaseQuickAdapter<WxPublic, BaseViewHolder> {

    constructor(layoutResId: Int) : super(layoutResId)

    constructor(layoutResId: Int, list: List<WxPublic>) : super(layoutResId, list)

    override fun convert(helper: BaseViewHolder?, item: WxPublic?) {
        val name: String = item?.name ?: ""
        helper?.setText(R.id.tv_wx_author, name)
            ?.setText(R.id.tv_wx_author_icon, name.substring(0, 1))
    }
}