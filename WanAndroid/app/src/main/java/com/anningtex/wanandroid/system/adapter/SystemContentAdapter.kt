package com.anningtex.wanandroid.system.adapter

import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.system.bean.SystemCategory
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * @ClassName:      SystemContentAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 11:45
 */
class SystemContentAdapter(layoutId: Int) :
    BaseQuickAdapter<SystemCategory, BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder?, item: SystemCategory?) {
        helper?.setText(R.id.tv_system_category, item?.name)
    }
}