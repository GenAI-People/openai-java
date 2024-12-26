package com.genaipeople.openai.tool.type;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ArrayTypeSerializer extends StdSerializer<ArrayType> {
    
    public ArrayTypeSerializer() {
        this(null);
    }

    public ArrayTypeSerializer(Class<ArrayType> t) {
        super(t);
    }

    @Override
    public void serialize(ArrayType value, JsonGenerator gen, SerializerProvider provider) 
        throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", "array");
        
        if (value.getDescription() != null) {
            gen.writeStringField("description", value.getDescription());
        }
        
        Class<?> itemType = value.getItemType();
        if (String.class.equals(itemType)) {
            gen.writeObjectFieldStart("items");
            gen.writeStringField("type", "string");
            gen.writeEndObject(); // end items
        } else if (Number.class.isAssignableFrom(itemType)) {
            gen.writeObjectFieldStart("items");
            gen.writeStringField("type", "number");
            gen.writeEndObject(); // end items
        } else {
            ObjectType objectType = SchemaGenerator.generateSchema(itemType);
            provider.defaultSerializeField("items", objectType, gen);
        }
        
        gen.writeEndObject(); // end array object
    }
} 