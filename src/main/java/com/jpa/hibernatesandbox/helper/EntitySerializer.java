package com.jpa.hibernatesandbox.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntitySerializer extends StdSerializer<Object> {

    public EntitySerializer() {
        this(null);
    }

    public EntitySerializer(Class<Object> t) {
        super(t);
    }

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            for (Method method : o.getClass().getDeclaredMethods()) {
                if (method.getName().matches("get.+Id$")) {
                    jsonGenerator.writeNumber((Integer) method.invoke(o));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException iEx) {
            System.out.println(iEx);
        }
    }
}
