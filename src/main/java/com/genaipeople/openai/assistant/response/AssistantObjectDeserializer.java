package com.genaipeople.openai.assistant.response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.genaipeople.openai.assistant.CodeInterpreterResource;
import com.genaipeople.openai.assistant.DeserializerUtil;
import com.genaipeople.openai.assistant.FileSearchResource;
import com.genaipeople.openai.assistant.ToolResource;
import com.genaipeople.openai.tool.CodeInterpreter;
import com.genaipeople.openai.tool.FileSearch;
import com.genaipeople.openai.tool.Function;
import com.genaipeople.openai.tool.Tool;

public class AssistantObjectDeserializer extends StdDeserializer<AssistantObject> {
    
    public AssistantObjectDeserializer() {
        this(null);
    }

    public AssistantObjectDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AssistantObject deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        AssistantObject assistant = new AssistantObject();
        
        assistant.setId(getTextValue(node, "id"));
        assistant.setObject(getTextValue(node, "object"));
        assistant.setCreatedAt(getLongValue(node, "created_at"));
        assistant.setName(getTextValue(node, "name"));
        assistant.setDescription(getTextValue(node, "description"));
        assistant.setModel(getTextValue(node, "model"));
        assistant.setInstructions(getTextValue(node, "instructions"));
        
        // Handle tools array
        if (node.has("tools") && node.get("tools").isArray()) {
            assistant.setTools(DeserializerUtil.deserializeTools(jp, node));
        }

        // Handle tool_resources
        if (node.has("tool_resources")) {
            JsonNode resourcesNode = node.get("tool_resources");
            if (resourcesNode.has("type")) {
                assistant.setToolResources(DeserializerUtil.deserializeToolResources(jp, resourcesNode));
            }
        }

        // Handle metadata
        if (node.has("metadata")) {
            assistant.setMetadata(DeserializerUtil.deserializeMetadata(jp, node));
        }

        assistant.setTemperature(getDoubleValue(node, "temperature"));
        assistant.setTopP(getDoubleValue(node, "top_p"));
        
        return assistant;
    }

    private String getTextValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }

    private Long getLongValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asLong() : null;
    }

    private Double getDoubleValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asDouble() : null;
    }
} 