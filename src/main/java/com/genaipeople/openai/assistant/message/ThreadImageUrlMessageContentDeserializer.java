package com.genaipeople.openai.assistant.message;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ThreadImageUrlMessageContentDeserializer extends StdDeserializer<ThreadImageUrlMessageContent> {
    
    public ThreadImageUrlMessageContentDeserializer() {
        this(null);
    }

    public ThreadImageUrlMessageContentDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ThreadImageUrlMessageContent deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode imageUrl = node.get("image_url");
        
        ThreadImageUrlMessageContent content = new ThreadImageUrlMessageContent(
            imageUrl.get("url").asText());
            
        if (imageUrl.has("detail")) {
            content.setDetail(imageUrl.get("detail").asText());
        }
        
        return content;
    }
} 