package com.genaipeople.openai.assistant.thread.run;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.genaipeople.openai.assistant.DeserializerUtil;
import com.genaipeople.openai.response.Usage;

public class RunObjectDeserializer extends StdDeserializer<RunObject> {
    
    public RunObjectDeserializer() {
        this(null);
    }

    public RunObjectDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RunObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        RunObject runObject = new RunObject();

        runObject.setId(getTextValue(node, "id"));
        runObject.setObject(getTextValue(node, "object"));
        runObject.setCreatedAt(getLongValue(node, "created_at"));
        runObject.setAssistantId(getTextValue(node, "assistant_id"));
        runObject.setThreadId(getTextValue(node, "thread_id"));
        runObject.setStatus(getTextValue(node, "status"));
        runObject.setStartedAt(getLongValue(node, "started_at"));
        runObject.setExpiresAt(getLongValue(node, "expires_at"));
        runObject.setCancelledAt(getLongValue(node, "cancelled_at"));
        runObject.setFailedAt(getLongValue(node, "failed_at"));
        runObject.setCompletedAt(getLongValue(node, "completed_at"));
        runObject.setModel(getTextValue(node, "model"));
        runObject.setInstructions(getTextValue(node, "instructions"));

         // Handle tools array
        if (node.has("tools") && node.get("tools").isArray()) {
            runObject.setTools(DeserializerUtil.deserializeTools(jp, node));
        }

        // Handle tool_resources
        if (node.has("tool_resources")) {
            JsonNode resourcesNode = node.get("tool_resources");
            if (resourcesNode.has("type")) {
                runObject.setToolResources(DeserializerUtil.deserializeToolResources(jp, resourcesNode));
            }
        }

        // Handle metadata
        if (node.has("metadata")) {
            runObject.setMetadata(DeserializerUtil.deserializeMetadata(jp, node));
        }

        if (node.has("usage")) {
            Usage usage = jp.getCodec().treeToValue(node.get("usage"), Usage.class);
            runObject.setUsage(usage);
        }

        if (node.has("required_action")) {
            RequiredAction requiredAction = jp.getCodec().treeToValue(node.get("required_action"), RequiredAction.class);
            runObject.setRequiredAction(requiredAction);
        }

        return runObject;
    }

    private String getTextValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }

    private Long getLongValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asLong() : null;
    }
} 