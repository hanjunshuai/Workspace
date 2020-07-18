package com.anningtex.anlogkotlin.http.gson

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @ClassName: GsonDConverterFactory
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/4/28 16:39
 */
class GsonDConverterFactory constructor(gson: Gson?) : Converter.Factory() {
    private val gson: Gson

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return GsonResponseBodyConverter<Any>(
            gson,
            type
        )
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        var adapter = gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter<Any>(
            gson,
            adapter
        )
    }

    companion object {
        @JvmOverloads
        fun create(gson: Gson? = Gson()): GsonDConverterFactory {
            return GsonDConverterFactory(gson)
        }
    }

    init {
        if (gson == null) throw NullPointerException("gson == null")
        this.gson = gson
    }
}