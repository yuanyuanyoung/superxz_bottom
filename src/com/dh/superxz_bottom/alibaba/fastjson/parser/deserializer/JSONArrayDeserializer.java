package com.dh.superxz_bottom.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;

import com.dh.superxz_bottom.alibaba.fastjson.JSONArray;
import com.dh.superxz_bottom.alibaba.fastjson.parser.DefaultExtJSONParser;
import com.dh.superxz_bottom.alibaba.fastjson.parser.JSONToken;

public class JSONArrayDeserializer implements ObjectDeserializer {
    public final static JSONArrayDeserializer instance = new JSONArrayDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultExtJSONParser parser, Type clazz) {
        JSONArray array = new JSONArray();
        parser.parseArray(array);
        return (T) array;
    }

    public int getFastMatchToken() {
        return JSONToken.LBRACKET;
    }
}
