package com.genaipeople.openai.tool.type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class TypeSerializer extends StdSerializer<Type<?>> {
    
    public TypeSerializer() {
        this(null);
    }

    public TypeSerializer(Class<Type<?>> t) {
        super(t);
    }

    @Override
    public void serialize(Type<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        if (value.getType() != null) {
            gen.writeStringField("type", value.getType());
        }
        
        if (value.getDescription() != null) {
            gen.writeStringField("description", value.getDescription());
        }

        // Handle arrays
        if (value instanceof List<?>) {
            gen.writeObjectFieldStart("items");
            
            List<?> list = (List<?>) value;
            if (!list.isEmpty()) {
                // Write array items
                gen.writeArrayFieldStart("items");
                for (Object item : list) {
                    if (item instanceof Type) {
                        serialize((Type<?>) item, gen, provider);
                    }
                }
                gen.writeEndArray();
            }
            gen.writeEndObject();
        }

        // Handle objects
        if (value.getType().equals("object")) {
            List<String> requiredProperties = new ArrayList<String>();
            if (value.getProperties() != null && !value.getProperties().isEmpty()) {
                gen.writeObjectFieldStart("properties");
                for (Map.Entry<String, Type<?>> entry : value.getProperties().entrySet()) {
                    gen.writeFieldName(entry.getKey());
                    serialize(entry.getValue(), gen, provider);
                    if (entry.getValue().getRequired()) {
                        requiredProperties.add(entry.getKey());
                    }
                }
                gen.writeEndObject();
            }
            if (!requiredProperties.isEmpty()) {
                gen.writeArrayFieldStart("required");
                for (String req : requiredProperties) {
                    gen.writeString(req);
                }
                gen.writeEndArray();
            }
        }

        // Handle enums
        if (value.getEnums() != null && !value.getEnums().isEmpty()) {
            gen.writeArrayFieldStart("enum");
            for (Object enumValue : value.getEnums()) {
                gen.writeObject(enumValue);
            }
            gen.writeEndArray();
        }

        gen.writeEndObject();
    }
} 