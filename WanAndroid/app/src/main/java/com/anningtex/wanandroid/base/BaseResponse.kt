package com.anningtex.wanandroid.base

/**
 *
 * @ClassName:      BaseResponse
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 11:38
 */
data class BaseResponse<T>(
    var data: T?,
    var results: T?,
    val errorMsg: String? = null,
    var errorCode: Int? = -1,
    var error: Boolean? = true
)