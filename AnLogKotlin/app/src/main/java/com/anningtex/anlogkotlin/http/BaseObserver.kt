package com.anningtex.anlogkotlin.http

import com.anningtex.anlogkotlin.base.BaseContract
import com.anningtex.anlogkotlin.entity.BaseResponse
import com.anningtex.baselibrary.util.LogUtil
import io.reactivex.observers.DisposableObserver

/**
 * @ClassName: BaseObserver
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/16 10:52
 */
abstract class BaseObserver<T> : DisposableObserver<BaseResponse<T>> {
    private var baseView: BaseContract.View? = null

    constructor(view: BaseContract.View?) : super() {
        baseView = view
    }

    override fun onStart() {
        super.onStart()
        if (baseView == null) {
            LogUtil.e("debug:" + "base view is null")
        }
        baseView?.showLoading()
    }

    override fun onNext(response: BaseResponse<T>) {
        baseView?.dismissLoading()
        LogUtil.e("debug:" + "response = ${response.toString()} ")
        val errorCode: Int = response.code
        val errorMsg: String = response.msg
        // var error: Boolean = response.error ?: true

        if ((errorCode == 1) or (errorCode == 200)) {
            onSuccess(errorCode, response.msg, response.data)
            LogUtil.d("debug：data${response.data.toString()}")
        } else if (errorCode == 0) {
            LogUtil.d("debug")
            onSuccess(errorCode, response.msg, response.data)
        } else {
            onError(ApiException(errorCode, errorMsg))
        }
    }

    override fun onError(e: Throwable) {
        ExceptionHandler.handleException(e)
    }

    abstract fun onSuccess(code: Int, msg: String, data: T)

    override fun onComplete() {
        baseView?.dismissLoading()
    }
}