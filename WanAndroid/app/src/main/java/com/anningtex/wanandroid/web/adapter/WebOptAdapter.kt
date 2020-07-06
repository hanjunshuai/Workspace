package com.anningtex.wanandroid.web.adapter

import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.web.bean.WebOptBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * @ClassName:      WebOptAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 9:34
 */
class WebOptAdapter(layoutResId: Int, dataList: ArrayList<WebOptBean>?) :
    BaseQuickAdapter<WebOptBean, BaseViewHolder>(layoutResId, dataList) {

    override fun convert(helper: BaseViewHolder?, item: WebOptBean?) {
        val id = item?.resId ?: -1
        if (id != -1) {
            helper?.setImageResource(R.id.iv_web_opt_img, id)
        }

        helper?.setText(R.id.tv_web_opt_title, item?.title)
    }
}