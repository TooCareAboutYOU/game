package com.kachat.game.libdata.gen;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class FloatConverter implements PropertyConverter<List<Float>, String> {

    private final Gson mGson;

    public FloatConverter() {
        mGson = new Gson();
    }

    @Override
    public List<Float> convertToEntityProperty(String databaseValue) {
        Type type = new TypeToken<ArrayList<Float>>() {
        }.getType();
        return mGson.fromJson(databaseValue , type);
    }

    @Override
    public String convertToDatabaseValue(List<Float> entityProperty) {
        return mGson.toJson(entityProperty);
    }
}
