package com.dh.superxz_bottom.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;

import com.dh.superxz_bottom.alibaba.fastjson.parser.DefaultExtJSONParser;

public interface ObjectDeserializer {
    <T> T deserialze(DefaultExtJSONParser parser, Type type);
    
    int getFastMatchToken();
}
