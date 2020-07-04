package com.anningtex.wanandroid.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: IntConverter
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/4 15:09
 */
public class IntConverter implements PropertyConverter<List<Integer>, String> {

    private final Gson mGson;

    public IntConverter() {
        mGson = new Gson();
    }

    @Override
    public List<Integer> convertToEntityProperty(String databaseValue) {
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> list = mGson.fromJson(databaseValue, type);
        return list;
    }

    @Override
    public String convertToDatabaseValue(List<Integer> entityProperty) {
        return mGson.toJson(entityProperty);
    }
}
