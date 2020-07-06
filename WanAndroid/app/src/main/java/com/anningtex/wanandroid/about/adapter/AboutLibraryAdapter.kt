package com.xing.wanandroid.setting.adapter

import com.anningtex.wanandroid.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class AboutLibraryAdapter : BaseQuickAdapter<String, BaseViewHolder> {

    constructor(layoutResId: Int) : super(layoutResId)

    constructor(layoutResId: Int, list: List<String>) : super(layoutResId, list)

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_about_library_name, item)
    }
}