package com.dh.superxz_bottom.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;

import com.dh.superxz_bottom.alibaba.fastjson.parser.DefaultExtJSONParser;
import com.dh.superxz_bottom.alibaba.fastjson.parser.JSONToken;

public class JavaObjectDeserializer implements ObjectDeserializer {

    public final static JavaObjectDeserializer instance = new JavaObjectDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultExtJSONParser parser, Type clazz) {
        return (T) parser.parse();
    }

    public int getFastMatchToken() {
        return JSONToken.LBRACE;
    }
}
