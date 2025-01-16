package com.genaipeople.openai.assistant.message;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ThreadImageFileMessageContentSerializer extends StdSerializer<ThreadImageFileMessageContent> {
    
    public ThreadImageFileMessageContentSerializer() {
        this(null);
    }

    public ThreadImageFileMessageContentSerializer(Class<ThreadImageFileMessageContent> t) {
        super(t);
    }

    @Override
    public void serialize(ThreadImageFileMessageContent value, JsonGenerator gen, SerializerProvider provider) 
        throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", "image_file");
        gen.writeObjectFieldStart("image_file");
        gen.writeStringField("file_id", value.getFileId());
        if (value.getDetail() != null) {
            gen.writeStringField("detail", value.getDetail());
        }
        gen.writeEndObject();
        gen.writeEndObject();
    }
} 