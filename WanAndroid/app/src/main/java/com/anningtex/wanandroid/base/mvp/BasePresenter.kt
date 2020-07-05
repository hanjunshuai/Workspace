package com.anningtex.wanandroid.base.mvp

import com.anningtex.wanandroid.base.BaseResponse
import com.anningtex.wanandroid.home.bean.Article
import com.anningtex.wanandroid.http.BaseObserver
import com.anningtex.wanandroid.http.RetrofitClient
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @ClassName:      BasePresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 11:30
 */
open class BasePresenter<V : IView> : IPresenter<V> {
    private lateinit var viewReference: WeakReference<V>
    private var disposable: CompositeDisposable = CompositeDisposable()

    fun <T> addSubscribe(observable: Observable<BaseResponse<T>>, baseObserver: BaseObserver<T>) {
        val observer = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(baseObserver)
        disposable.add(observer)
    }

    fun unsubscribe() {
        disposable.dispose()
    }

    fun <D> create(clazz: Class<D>): D {
        return RetrofitClient.get().retrofit.create(clazz)
    }


    override fun attachView(view: V) {
        viewReference = WeakReference(view)
    }

    override fun detachView() {
        viewReference.clear()
    }

    override fun isViewAttached(): Boolean {
        return viewReference.get() != null
    }

    override fun getView(): V? {
        return viewReference.get()
    }
}