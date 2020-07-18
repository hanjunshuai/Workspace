package com.anningtex.anlogkotlin.http.gson;

import android.util.Log;

import com.anningtex.anlogkotlin.entity.BaseResponse;
import com.anningtex.anlogkotlin.http.ApiException;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @ClassName: GsonResponseBodyConverter
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/4/28 16:40
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /**
     * 针对数据返回成功、错误不同类型字段处理
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            BaseResponse result = gson.fromJson(response, BaseResponse.class);
            int code = result.getCode();
            if (code == 1) {
                return gson.fromJson(response, type);
            } else {
                Log.d("HttpManager", "返回err==：" + response);
                BaseResponse errResponse = gson.fromJson(response, BaseResponse.class);
                if (code == 0) {
                    ApiException exception = new ApiException(code, errResponse.getMsg());
                    exception.setErrMsg(errResponse.getMsg());
                    exception.setErrCode(0);
                    throw exception;
                } else {
                    throw new ApiException(code, errResponse.getMsg());
                }
            }
        } finally {
            value.close();
        }
    }
}
