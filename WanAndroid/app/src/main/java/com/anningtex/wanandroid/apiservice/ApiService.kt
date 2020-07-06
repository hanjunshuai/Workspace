package com.anningtex.wanandroid.apiservice

import com.anningtex.wanandroid.base.BaseResponse
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.home.bean.ArticleResponse
import com.anningtex.wanandroid.home.bean.Banner
import com.anningtex.wanandroid.project.bean.ProjectResponse
import com.anningtex.wanandroid.project.bean.ProjectTab
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("project/tree/json")
    fun getProjectTabs(): Observable<BaseResponse<List<ProjectTab>>>

    @GET("project/list/{page}/json")
    fun getProjectLists(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<BaseResponse<ProjectResponse>>
}