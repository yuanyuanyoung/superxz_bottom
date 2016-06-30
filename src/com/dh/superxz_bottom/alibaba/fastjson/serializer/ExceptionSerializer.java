package com.dh.superxz_bottom.alibaba.fastjson.serializer;

public class ExceptionSerializer extends JavaBeanSerializer {

    public ExceptionSerializer(Class<?> clazz){
        super(clazz);
    }

    @Override
	protected boolean isWriteClassName(JSONSerializer serializer) {
        return true;
    }
}
