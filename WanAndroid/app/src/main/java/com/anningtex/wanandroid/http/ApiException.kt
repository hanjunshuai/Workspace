package com.anningtex.wanandroid.http

/**
 *
 * @ClassName:      ApiException
 * @Description:     java类作用描述
 * @Author:         alvis
 * @CreateDate:     2020/7/4 11:54
 */
data class ApiException(var errCode: Int, var errMsg: String) : Exception()