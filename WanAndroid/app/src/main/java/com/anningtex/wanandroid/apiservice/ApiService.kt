package com.anningtex.wanandroid.apiservice

import com.anningtex.wanandroid.base.BaseResponse
import com.anningtex.wanandroid.gank.bean.GankToday
import com.anningtex.wanandroid.gank.bean.WxPublic
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.home.bean.ArticleResponse
import com.anningtex.wanandroid.home.bean.Banner
import com.anningtex.wanandroid.project.bean.ProjectResponse
import com.anningtex.wanandroid.project.bean.ProjectTab
import com.anningtex.wanandroid.system.bean.SystemCategory
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.HashMap

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

    @GET("tree/json")
    fun getSystem(): Observable<BaseResponse<List<SystemCategory>>>

    @GET("article/list/{page}/json")
    fun getSystemArticles(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<BaseResponse<ArticleResponse>>

    @GET("http://gank.io/api/today")
    fun getGankToday(): Observable<BaseResponse<HashMap<String, List<GankToday>>>>

    @GET("wxarticle/chapters/json")
    fun getWxPublic(): Observable<BaseResponse<List<WxPublic>>>

    @GET("wxarticle/list/{id}/{page}/json")
    fun getWxPublicArticle(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): Observable<BaseResponse<ArticleResponse>>

}