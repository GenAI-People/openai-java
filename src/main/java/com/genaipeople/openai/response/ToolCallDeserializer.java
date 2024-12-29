package com.genaipeople.openai.response;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ToolCallDeserializer extends StdDeserializer<ToolCall> {
    
    public ToolCallDeserializer() {
        this(null);
    }

    public ToolCallDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ToolCall deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        ToolCall toolCall = new ToolCall();
        
        if (node.has("id")) {
            toolCall.setId(node.get("id").asText());
        }
        
        if (node.has("type")) {
            toolCall.setType(node.get("type").asText());
        }
        
        if (node.has("function")) {
            Function function = jp.getCodec().treeToValue(node.get("function"), Function.class);
            toolCall.setFunction(function);
        }

        return toolCall;
    }
} 