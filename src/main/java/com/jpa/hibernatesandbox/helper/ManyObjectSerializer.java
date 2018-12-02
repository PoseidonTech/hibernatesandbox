package com.jpa.hibernatesandbox.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jpa.hibernatesandbox.domain.ManyObject;

import java.io.IOException;

public class ManyObjectSerializer extends StdSerializer<ManyObject> {

    public ManyObjectSerializer() {
        this(null);
    }

    public ManyObjectSerializer(Class<ManyObject> t) {
        super(t);
    }

    @Override
    public void serialize(ManyObject manyObject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("manyObjectId",manyObject.getManyObjectId());
        jsonGenerator.writeStringField("name",manyObject.getName());
        jsonGenerator.writeEndObject();
    }
}
