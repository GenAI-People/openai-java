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
            List<Tool> tools = new ArrayList<>();
            for (JsonNode toolNode : node.get("tools")) {
                String type = toolNode.get("type").asText();
                Tool tool;
                switch (type) {
                    case "code_interpreter":
                        tool = new CodeInterpreter();
                        break;
                    case "file_search":
                        tool = new FileSearch();
                        break;
                    case "function":
                        tool = jp.getCodec().treeToValue(toolNode, Function.class);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown tool type: " + type);
                }
                tools.add(tool);
            }
            assistant.setTools(tools);
        }

        // Handle tool_resources
        if (node.has("tool_resources")) {
            JsonNode resourcesNode = node.get("tool_resources");
            if (resourcesNode.has("type")) {
                String type = resourcesNode.get("type").asText();
                ToolResource resources;
                switch (type) {
                    case "code_interpreter":
                        resources = jp.getCodec().treeToValue(resourcesNode, CodeInterpreterResource.class);
                        break;
                    case "file_search":
                        resources = jp.getCodec().treeToValue(resourcesNode, FileSearchResource.class);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown resource type: " + type);
                }
                assistant.setToolResources(resources);
            }
        }

        // Handle metadata
        if (node.has("metadata")) {
            @SuppressWarnings("unchecked")
            Map<String, String> metadata = jp.getCodec().treeToValue(node.get("metadata"), Map.class);
            assistant.setMetadata(metadata);
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