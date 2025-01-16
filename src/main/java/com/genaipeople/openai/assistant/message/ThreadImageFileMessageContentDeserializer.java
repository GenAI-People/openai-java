package com.genaipeople.openai.assistant.message;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ThreadImageFileMessageContentDeserializer extends StdDeserializer<ThreadImageFileMessageContent> {
    
    public ThreadImageFileMessageContentDeserializer() {
        this(null);
    }

    public ThreadImageFileMessageContentDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ThreadImageFileMessageContent deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode imageFile = node.get("image_file");
        
        ThreadImageFileMessageContent content = new ThreadImageFileMessageContent(
            imageFile.get("file_id").asText());
            
        if (imageFile.has("detail")) {
            content.setDetail(imageFile.get("detail").asText());
        }
        
        return content;
    }
} 