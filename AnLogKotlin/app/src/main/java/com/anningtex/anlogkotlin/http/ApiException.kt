package com.anningtex.anlogkotlin.http

import java.io.IOException

/**
 * @ClassName: ApiException
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/16 10:54
 */
data class ApiException(var errCode: Int, var errMsg: String) : IOException()