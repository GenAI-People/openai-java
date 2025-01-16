package com.genaipeople.openai.assistant.thread.run;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.genaipeople.openai.tool.Function;

public class ToolChoiceDeserializer extends StdDeserializer<ToolChoice> {
    
    public ToolChoiceDeserializer() {
        this(null);
    }

    public ToolChoiceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ToolChoice deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        ToolChoice toolChoice = new ToolChoice();
        
        if (node.isTextual()) {
            toolChoice.setType(ToolChoiceType.valueOf(node.asText()));
        } else if (node.isObject()) {
            toolChoice.setType(ToolChoiceType.valueOf(getTextValue(node, "type")));
            if (node.has("function")) {
                JsonNode function = node.get("function");
                Function functionNode = new Function();
                functionNode.setName(getTextValue(function, "name"));
                toolChoice.setTool(functionNode);
            }
        }
        
        return toolChoice;
    }

    private String getTextValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }
} 