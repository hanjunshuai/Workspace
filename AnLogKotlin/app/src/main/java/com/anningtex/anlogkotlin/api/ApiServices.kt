package com.anningtex.anlogkotlin.api

import com.anningtex.anlogkotlin.entity.BaseResponse
import com.anningtex.anlogkotlin.entity.LoginResponse
import com.anningtex.anlogkotlin.entity.qc.DeliveryGoodsResponse
import com.anningtex.anlogkotlin.entity.qc.QcOrderResponse
import io.reactivex.Observable
import okhttp3.Call
import org.json.JSONArray
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @ClassName: ApiServices
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/1/13 14:52
 */
interface ApiServices {
    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/login_in")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("device_id") deviceId: String
    ): Observable<BaseResponse<LoginResponse>>


    // qc 检验员
    /**
     * 登录获取QC专属布产单列表
     *
     * @return
     */
    @GET("api/v1/getQcOrder")
    fun getQcOrder(): Observable<BaseResponse<MutableList<QcOrderResponse>>>


    /**
     * 4.37.登录获取QC专属已排单和路上货物列表
     *
     * @return
     */
    @GET("api/v1/getDeliveryGoodsList")
    fun getDeliveryGoodsList(): Observable<BaseResponse<MutableList<DeliveryGoodsResponse>>>
}