package com.anningtex.wanandroid.home.bean

/**
 *
 * @ClassName:      ArticleResponse
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 16:57
 */
data class ArticleResponse(
    val curPage: Int,
    val pageCount: Int,
    val datas: List<Article>?
)