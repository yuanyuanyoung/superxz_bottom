package com.dh.superxz_bottom.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicIntegerArray;

import com.dh.superxz_bottom.alibaba.fastjson.JSONArray;
import com.dh.superxz_bottom.alibaba.fastjson.parser.DefaultExtJSONParser;
import com.dh.superxz_bottom.alibaba.fastjson.parser.JSONToken;

public class AtomicIntegerArrayDeserializer implements ObjectDeserializer {

    public final static AtomicIntegerArrayDeserializer instance = new AtomicIntegerArrayDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultExtJSONParser parser, Type clazz) {
        if (parser.getLexer().token() == JSONToken.NULL) {
            parser.getLexer().nextToken(JSONToken.COMMA);
            return null;
        }

        JSONArray array = new JSONArray();
        parser.parseArray(array);

        AtomicIntegerArray atomicArray = new AtomicIntegerArray(array.size());
        for (int i = 0; i < array.size(); ++i) {
            atomicArray.set(i, array.getInteger(i));
        }

        return (T) atomicArray;
    }

    public int getFastMatchToken() {
        return JSONToken.LBRACKET;
    }
}
