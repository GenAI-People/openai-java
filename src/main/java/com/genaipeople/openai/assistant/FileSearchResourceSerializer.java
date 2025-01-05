package com.genaipeople.openai.assistant;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class FileSearchResourceSerializer extends StdSerializer<FileSearchResource> {
    
    public FileSearchResourceSerializer() {
        this(null);
    }

    public FileSearchResourceSerializer(Class<FileSearchResource> t) {
        super(t);
    }

    @Override
    public void serialize(FileSearchResource value, JsonGenerator gen, SerializerProvider provider) 
        throws IOException {
        gen.writeStartObject();
        gen.writeObjectFieldStart("file_search");
        gen.writeArrayFieldStart("vector_store_ids");
        for (String id : value.getVectorStoreIds()) {
            gen.writeString(id);
        }
        gen.writeEndArray();
        gen.writeEndObject();
        gen.writeEndObject();
    }
} 