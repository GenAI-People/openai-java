package com.genaipeople.openai.assistant.thread;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.genaipeople.openai.assistant.CodeInterpreterResource;
import com.genaipeople.openai.assistant.FileSearchResource;
import com.genaipeople.openai.assistant.ToolResource;

public class ThreadObjectDeserializer extends StdDeserializer<ThreadObject> {
    
    public ThreadObjectDeserializer() {
        this(null);
    }

    public ThreadObjectDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ThreadObject deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        ThreadObject thread = new ThreadObject();
        
        thread.setId(getTextValue(node, "id"));
        thread.setObject(getTextValue(node, "object"));
        thread.setCreatedAt(getLongValue(node, "created_at"));
        
        // Handle metadata
        if (node.has("metadata")) {
            @SuppressWarnings("unchecked")
            Map<String, String> metadata = jp.getCodec().treeToValue(node.get("metadata"), Map.class);
            thread.setMetadata(metadata);
        }

        // Handle tool_resources
        if (node.has("tool_resources")) {
            JsonNode toolResources = node.get("tool_resources");
            ToolResource resources = null;

            if (toolResources.has("code_interpreter")) {
                JsonNode codeInterpreter = toolResources.get("code_interpreter");
                if (codeInterpreter.has("file_ids")) {
                    resources = jp.getCodec().treeToValue(
                        codeInterpreter, CodeInterpreterResource.class);
                }
            }

            if (toolResources.has("file_search")) {
                JsonNode fileSearch = toolResources.get("file_search");
                if (fileSearch.has("vector_store_ids")) {
                    resources = jp.getCodec().treeToValue(
                        fileSearch, FileSearchResource.class);
                }
            }

            thread.setToolResources(resources);
        }
        
        return thread;
    }

    private String getTextValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }

    private Long getLongValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asLong() : null;
    }
} 