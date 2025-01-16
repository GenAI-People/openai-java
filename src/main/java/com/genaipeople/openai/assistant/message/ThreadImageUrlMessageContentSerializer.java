package com.genaipeople.openai.assistant.message;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ThreadImageUrlMessageContentSerializer extends StdSerializer<ThreadImageUrlMessageContent> {
    
    public ThreadImageUrlMessageContentSerializer() {
        this(null);
    }

    public ThreadImageUrlMessageContentSerializer(Class<ThreadImageUrlMessageContent> t) {
        super(t);
    }

    @Override
    public void serialize(ThreadImageUrlMessageContent value, JsonGenerator gen, SerializerProvider provider) 
        throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", "image_url");
        gen.writeObjectFieldStart("image_url");
        gen.writeStringField("url", value.getImageUrl());
        if (value.getDetail() != null) {
            gen.writeStringField("detail", value.getDetail());
        }
        gen.writeEndObject();
        gen.writeEndObject();
    }
} 