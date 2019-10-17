package com.cn.ql.frame.net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by jaycee on 2017/6/23.
 */
public class RetrofitAdapter implements JsonDeserializer<JsonElement> {
    @Override
    public JsonElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            return json;
        }
        return null;
    }
}
