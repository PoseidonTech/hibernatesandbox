package com.jpa.hibernatesandbox.helper.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.jpa.hibernatesandbox.helper.DeserializerHelper;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntityDeserializer<T> extends StdDeserializer<T> {

    public EntityDeserializer() {
        this(null);
    }

    public EntityDeserializer(Class<T> vc) {
        super(vc);
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        T returnObject = (T) new Object();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Class clazz = returnObject.getClass();

//        String fieldName = ((Character) methodName.charAt(3)).toString().toLowerCase() +  methodName.substring(4, methodName.length() - 2);

        HashMap<String, Field> fieldNameMap = new HashMap<>();
        HashMap<String, Class> fieldClassMap = new HashMap<>();
        HashMap<String, Method> setterMap = new HashMap<>();
        HashMap<Field, Method> fieldToSetterMap = new HashMap<>();


        //populate fieldNameMap
        for (Field field : clazz.getFields()) {
            String fieldName = field.getName();
            fieldNameMap.put(fieldName, field);
            fieldClassMap.put(fieldName, field.getType());
        }

        //populate getterMap and setterMap
        for (Method method : clazz.getMethods()) {
            String methodName = method.getName();
            String fieldName = ((Character) methodName.charAt(3)).toString().toLowerCase() + methodName.substring(4, methodName.length() - 2);
            if(methodName.matches("set.+Id$")) {
                setterMap.put(fieldName,method);
                fieldToSetterMap.put(fieldNameMap.get(fieldName),method);
            }
        }

        try {
            //loop over fields of input json
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> thisNodeMapEntry = fields.next();

                String keyName = thisNodeMapEntry.getKey();
                JsonNode thisNodeValue = thisNodeMapEntry.getValue();

                Method setter = setterMap.get(keyName);
                //handle value node case
                if(thisNodeValue.isValueNode()) {
                    Class jsonKeyClass = fieldClassMap.get(keyName);
                    //base case, not a child object
                    if(jsonKeyClass.isInstance(String.class) || jsonKeyClass.isPrimitive()) {
                        setter.invoke(returnObject, fieldClassMap.get(keyName).cast(thisNodeValue));
                    } else {//field is a child object, instantiate an instance of the object and set its id to field's value
                        Object childObject = jsonKeyClass.getConstructor().newInstance();
                        Method childObjectIdSetter = null;
                        for(Method method : jsonKeyClass.getMethods()) {
                            if(method.getName().matches("set.+Id$")) {
                                childObjectIdSetter = method;
                            }
                        }
                        //set new child object instance's id
                        childObjectIdSetter.invoke(childObject, childObjectIdSetter.getReturnType().cast(thisNodeValue));
                        //set new child object instance to parent
                        setter.invoke(returnObject, childObject);
                    }
                }
                //handle container node case
                if(thisNodeValue.isContainerNode()) {

                }
                //handle array case
                if(thisNodeValue.isArray()) {

                }

            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException ex) {
            ex.printStackTrace();
        }
        return returnObject;
    }
}
