package com.jpa.hibernatesandbox.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EntityListSerializer extends StdSerializer<Object> {

    public EntityListSerializer() {
        this(null);
    }

    public EntityListSerializer(Class<Object> t) {
        super(t);
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        HashMap<String, Method> methodMap = new HashMap<>();
        Class clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().contains("get")) {
                methodMap.put(method.getName().substring(3).toLowerCase(), method);
            }
        }
        try {
            for (Field field : clazz.getDeclaredFields()) {
                Class fieldClazz = field.getType();
                String fieldName = field.getName();
                Method getter = methodMap.get(fieldName.toLowerCase());

                //handle collection case with interface check
                if (getter != null && !fieldClazz.isInterface()) {
                    if (fieldClazz.getSuperclass().equals(Number.class)) {
                        if (fieldClazz.equals(Short.class)) {
                            jsonGenerator.writeNumberField(fieldName, (Short) getter.invoke(object));
                        }
                        if (fieldClazz.equals(Integer.class)) {
                            jsonGenerator.writeNumberField(fieldName, (Integer) getter.invoke(object));
                        }
                        if (fieldClazz.equals(Long.class)) {
                            jsonGenerator.writeNumberField(fieldName, (Long) getter.invoke(object));
                        }
                        if (fieldClazz.equals(Float.class)) {
                            jsonGenerator.writeNumberField(fieldName, (Float) getter.invoke(object));
                        }
                        if (fieldClazz.equals(Double.class)) {
                            jsonGenerator.writeNumberField(fieldName, (Double) getter.invoke(object));
                        }
                    } else if (fieldClazz.equals(String.class) || fieldClazz.equals(Character.class)) {
                        jsonGenerator.writeStringField(fieldName, (String) getter.invoke(object));
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException iEx) {
            System.out.println(iEx);
        }

        jsonGenerator.writeEndObject();
    }
}
