package com.anningtex.wanandroid.gank.bean

import com.chad.library.adapter.base.entity.SectionEntity

/**
 *
 * @ClassName:      GankTodayEntity
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 16:08
 */
class GankTodayEntity<T> : SectionEntity<T> {
    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(t: T) : super(t)

}