package com.genaipeople.openai.assistant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.genaipeople.openai.tool.CodeInterpreter;
import com.genaipeople.openai.tool.FileSearch;
import com.genaipeople.openai.tool.Function;
import com.genaipeople.openai.tool.Tool;

public class DeserializerUtil {
    public static List<Tool> deserializeTools(JsonParser jp, JsonNode node) throws JsonProcessingException {
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
        return tools;
    }

    public static ToolResource deserializeToolResources(JsonParser jp, JsonNode node) throws JsonProcessingException {
        String type = node.get("type").asText();
        ToolResource resources;
        switch (type) {
            case "code_interpreter":
                resources = jp.getCodec().treeToValue(node, CodeInterpreterResource.class);
                break;
            case "file_search":
                resources = jp.getCodec().treeToValue(node, FileSearchResource.class);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown resource type: " + type);
            }   
        return resources;
    }

    public static Map<String, String> deserializeMetadata(JsonParser jp, JsonNode node) throws JsonProcessingException {
        @SuppressWarnings("unchecked")
        Map<String, String> metadata = jp.getCodec().treeToValue(node.get("metadata"), Map.class);
        return metadata;
    }
}
