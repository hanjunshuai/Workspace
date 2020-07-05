package com.anningtex.wanandroid.apiservice

import com.anningtex.wanandroid.base.BaseResponse
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.home.bean.ArticleResponse
import com.anningtex.wanandroid.home.bean.Banner
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @ClassName:      ApiService
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 17:09
 */
interface ApiService {
    @GET("banner/json")
    fun getBanner(): Observable<BaseResponse<List<Banner>>>

    @GET("article/top/json")
    fun getTopArticle(): Observable<BaseResponse<List<Article>>>

    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: Int): Observable<BaseResponse<ArticleResponse>>
}