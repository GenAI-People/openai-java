package com.genaipeople.openai.assistant.message;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ThreadMessageRequestSerializer extends StdSerializer<ThreadMessageRequest> {
    
    public ThreadMessageRequestSerializer() {
        this(null);
    }

    public ThreadMessageRequestSerializer(Class<ThreadMessageRequest> t) {
        super(t);
    }

    @Override
    public void serialize(ThreadMessageRequest value, JsonGenerator gen, SerializerProvider provider) 
        throws IOException {
        gen.writeStartObject();
        gen.writeStringField("role", value.getRole().toString());

        // Handle content field
        if (value.getContent() != null && value.getContent().size() == 1 
            && value.getContent().get(0) instanceof ThreadTextMessageContent) {
            gen.writeStringField("content", ((ThreadTextMessageContent)value.getContent().get(0)).getText());
        } else {
            gen.writeObjectField("content", value.getContent());
        }

        if (value.getAttachments() != null) {
            gen.writeObjectField("attachments", value.getAttachments());
        }
        if (value.getMetadata() != null) {
            gen.writeObjectField("metadata", value.getMetadata());
        }
        gen.writeEndObject();
    }
} 