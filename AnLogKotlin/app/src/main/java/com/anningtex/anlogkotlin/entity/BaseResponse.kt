package com.anningtex.anlogkotlin.entity

/**
 * @ClassName: BaseResponse
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/16 10:50
 */
class BaseResponse<T>(var code: Int, var msg: String, var data: T) {
    override fun toString(): String {
        return "BaseResponse(code=$code, msg='$msg', data=$data)"
    }
}