package com.anningtex.wanandroid.home.presenter

import com.anningtex.wanandroid.apiservice.ApiService
import com.anningtex.wanandroid.base.BaseResponse
import com.anningtex.wanandroid.base.mvp.BasePresenter
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.home.bean.ArticleResponse
import com.anningtex.wanandroid.home.bean.Banner
import com.anningtex.wanandroid.home.contract.HomeContract
import com.anningtex.wanandroid.http.BaseObserver
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 *
 * @ClassName:      HomePresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 16:38
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    private var dataList = ArrayList<Article>()
    fun getBanner() {
        addSubscribe(
            create(ApiService::class.java).getBanner(),
            object : BaseObserver<List<Banner>>() {
                override fun onSuccess(data: List<Banner>?) {
                    getView()?.onBanner(data)
                }
            })
    }

    fun getArticles(page: Int) {
        val apiService = create(ApiService::class.java)
        val zipObserver = Observable.zip(apiService.getTopArticle(), apiService.getArticles(page),
            object : BiFunction<BaseResponse<List<Article>>,
                    BaseResponse<ArticleResponse>, BaseResponse<List<Article>>> {
                override fun apply(
                    resp1: BaseResponse<List<Article>>,
                    resp2: BaseResponse<ArticleResponse>
                ): BaseResponse<List<Article>> {
                    if (page == 0) {
                        dataList.clear()
                        val topArticles = resp1.data
                        if (topArticles != null) {
                            dataList.addAll(topArticles)
                        }
                    }
                    val data = resp2.data
                    if (data != null) {
                        val articles = data.datas
                        if (articles != null) {
                            dataList.addAll(articles)
                        }
                    }
                    // 因为 BaseObserver 泛型指定为了 BaseObserver ,所以这里重新构造 BaseObserver 对象作为返回值
                    return BaseResponse(dataList,dataList,resp1.errorMsg,resp1.errorCode,false)
                }
            })

        addSubscribe(zipObserver,object :BaseObserver<List<Article>>(){
            override fun onSuccess(data: List<Article>?) {
                getView()?.onArticles(page,data)
            }

        })
    }
}