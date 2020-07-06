package com.anningtex.wanandroid.web.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.BaseResponse
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.http.BaseObserver
import com.anningtex.wanandroid.web.contract.WebContract
import io.reactivex.Observable

/**
 *
 * @ClassName:      WebPresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/6 8:28
 */
class WebPresenter : BasePresenter<WebContract.View>(), WebContract.Presenter {
    fun addFavorite(id: Int, title: String, author: String, link: String) {
        val observable: Observable<BaseResponse<AddFavoriteResponse>>
        if (id == -1) {
            observable = create(ApiService::class.java).addFavorite(title, author, link)
        } else {
            observable = create(ApiService::class.java).addFavorite(id)
        }
        addSubscribe(observable, object : BaseObserver<AddFavoriteResponse>() {
            override fun onSuccess(data: AddFavoriteResponse?) {
                getView()?.onAddFavorited(data)
            }
        })
    }
}