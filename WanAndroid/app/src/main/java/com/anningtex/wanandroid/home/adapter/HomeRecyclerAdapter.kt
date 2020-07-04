package com.anningtex.wanandroid.home.adapter

import android.text.TextUtils
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.home.bean.Article
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.lang.String.format

/**
 *
 * @ClassName:      HomeRecyclerAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 16:48
 */
class HomeRecyclerAdapter(layoutResId: Int) :
    BaseQuickAdapter<Article, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: Article?) {
        // type： 是否是置顶    type = 1 -> 置顶 ， type = 0 -> 非置顶
        val type = item?.type ?: 0
        // refresh: 是否是最新， true -> 最新 ， false -> 非最新
        val isRefresh = item?.fresh ?: false
        var tip: String? = null
        if (type == 1) {
            tip = "置顶"
        } else if (isRefresh) {
            tip = "最新"
        }
        helper?.setText(R.id.tv_home_title, item?.title)
            ?.setText(R.id.tv_home_author, item?.author)
            ?.setText(
                R.id.tv_home_public_time,
                format((item?.publishTime ?: System.currentTimeMillis()).toString())
            )
            ?.setText(R.id.tv_home_category, item?.superChapterName)
            ?.setGone(
                R.id.tv_home_recent,
                (type == 1 || isRefresh) && !TextUtils.isEmpty(tip)
            )   // true 显示，false 隐藏
            ?.setText(R.id.tv_home_recent, tip)
    }
}