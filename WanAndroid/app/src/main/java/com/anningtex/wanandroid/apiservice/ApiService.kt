package com.anningtex.wanandroid.apiservice

import com.anningtex.wanandroid.base.BaseResponse
import com.anningtex.wanandroid.home.bean.Banner
import io.reactivex.Observable
import retrofit2.http.GET

/**
 *
 * @ClassName:      ApiService
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 17:09
 */
interface ApiService {
    @GET("")
    fun getBanner(): Observable<BaseResponse<List<Banner>>>
}