package com.anningtex.wanandroid.project.adapter

import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.project.bean.Project
import com.anningtex.wanandroid.util.createColorDrawable
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * @ClassName:      ProjectAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 10:36
 */
class ProjectAdapter(layoutId: Int) : BaseQuickAdapter<Project, BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder?, item: Project?) {
        helper?.setText(R.id.tv_project_title, item?.title)
            ?.setText(R.id.tv_project_author, item?.author)
            ?.setText(R.id.tv_project_time, item?.niceDate)
            ?.addOnClickListener(R.id.iv_project_image)    // 设置子 view 可以响应点击事件

        val colorDrawable = createColorDrawable(mContext)
        Glide.with(mContext)
            .load(item?.envelopePic)
            .placeholder(colorDrawable)
            .error(colorDrawable)
            .into(helper!!.getView(R.id.iv_project_image))
    }
}