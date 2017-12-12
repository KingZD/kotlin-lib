package com.zed.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Date  : 2017-05-19
 * <p>
 * Desc  :
 */
public class GsonUtils {

    private GsonUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static <T> T convertBean(Object data, Class<T> classOfT) {
        Gson gson = new Gson();
        T t;
        try {
            t = gson.fromJson(gson.toJson(data), classOfT);
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> convertBean(Object data, TypeToken typeToken) {
        Gson gson = new Gson();
        List<T> t;
        try {
            t = gson.fromJson(gson.toJson(data), typeToken.getType());
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T convertJson2Bean(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        T t;
        try {
            t = gson.fromJson(json, classOfT);
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> convertJson2Bean(String json, TypeToken typeToken) {
        Gson gson = new Gson();
        List<T> t;
        try {
            t = gson.fromJson(json, typeToken.getType());
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    public static String obj2Str(Object data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public static JsonObject str2Obj(String jsonStr) {
        try {
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(jsonStr).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getPropertyFromObj(JsonObject jsonObj, String key) {
        try {
            return jsonObj.get(key).getAsString();
        } catch (Exception e) {
            return null;
        }
    }
}
