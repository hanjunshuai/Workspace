package com.anningtex.wanandroid.img.adapter

import com.anningtex.wanandroid.R
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.chrisbanes.photoview.PhotoView

/**
 *
 * @ClassName:      ImageBrowseAdapter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 11:34
 */
class ImageBrowseAdapter(layoutResId: Int) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        val photoView: PhotoView? = helper?.getView(R.id.pv_image)
        Glide.with(mContext).load(item ?: "").into(photoView!!)
    }
}