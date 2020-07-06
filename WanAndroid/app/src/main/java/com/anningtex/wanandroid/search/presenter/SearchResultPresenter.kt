package com.xing.wanandroid.search.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.http.BaseObserver
import com.xing.wanandroid.search.bean.SearchResultResponse
import com.xing.wanandroid.search.contract.SearchResultContract

class SearchResultPresenter : BasePresenter<SearchResultContract.View>(), SearchResultContract.Presenter {
    override fun getSearchResult(page: Int, keyword: String?) {
        if (keyword == null) {
            return
        }
        addSubscribe(
            create(ApiService::class.java).getSearchResult(page, keyword),
            object : BaseObserver<SearchResultResponse>() {
                override fun onSuccess(response: SearchResultResponse?) {
                    if (this@SearchResultPresenter.isViewAttached()) {
                        this@SearchResultPresenter.getView()?.onSearchResult(page, response)
                    }
                }
            })
    }


}