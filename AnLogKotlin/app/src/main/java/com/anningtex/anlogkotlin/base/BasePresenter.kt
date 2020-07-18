package com.anningtex.anlogkotlin.base

import com.anningtex.anlogkotlin.entity.BaseResponse
import com.anningtex.anlogkotlin.http.BaseObserver
import com.anningtex.anlogkotlin.http.RetrofitClient
import com.anningtex.baselibrary.base.AbsBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

/**
 *
 * @ClassName:      BasePresenter
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/16 9:02
 */
open class BasePresenter<V : BaseContract.View> : AbsBasePresenter<V>(), BaseContract.Presenter<V> {
    //    private lateinit var viewReference: WeakReference<V>
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

}