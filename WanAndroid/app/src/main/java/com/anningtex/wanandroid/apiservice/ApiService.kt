package com.anningtex.wanandroid.apiservice

import com.anningtex.wanandroid.base.BaseResponse
import com.anningtex.wanandroid.db.bean.User
import com.anningtex.wanandroid.gank.bean.GankToday
import com.anningtex.wanandroid.gank.bean.WxPublic
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.home.bean.ArticleResponse
import com.anningtex.wanandroid.home.bean.Banner
import com.anningtex.wanandroid.project.bean.ProjectResponse
import com.anningtex.wanandroid.project.bean.ProjectTab
import com.anningtex.wanandroid.system.bean.SystemCategory
import com.anningtex.wanandroid.web.bean.AddFavoriteResponse
import com.xing.wanandroid.meizi.bean.Meizi
import com.xing.wanandroid.search.bean.SearchHot
import com.xing.wanandroid.search.bean.SearchResultResponse
import com.xing.wanandroid.setting.bean.LogoutResult
import com.xing.wanandroid.user.bean.RegisterResponse
import io.reactivex.Observable
import retrofit2.http.*
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

    @GET("http://gank.io/api/data/福利/{pageSize}/{page}")
    fun getMeiziList(
        @Path("pageSize") pageSize: Int,
        @Path("page") page: Int
    ): Observable<BaseResponse<List<Meizi>>>

    @GET("lg/collect/list/{page}/json")
    fun getArticleFavorites(@Path("page") page: Int): Observable<BaseResponse<ArticleResponse>>

    @GET("user/logout/json")
    fun logout(): Observable<BaseResponse<LogoutResult>>

    @POST("user/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<BaseResponse<User>>

    @POST("user/register")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Observable<BaseResponse<RegisterResponse>>

    @GET("hotkey/json")
    fun getSearchHot(): Observable<BaseResponse<ArrayList<SearchHot>>>

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    fun getSearchResult(
        @Path("page") page: Int,
        @Field("k") keyword: String
    ): Observable<BaseResponse<SearchResultResponse>>


    @POST("lg/collect/{id}/json")
    fun addFavorite(@Path("id") id: Int): Observable<BaseResponse<AddFavoriteResponse>>


    @POST("lg/collect/add/json")
    @FormUrlEncoded
    fun addFavorite(@Field("title") title: String, @Field("author") author: String, @Field("link") link: String): Observable<BaseResponse<AddFavoriteResponse>>

}