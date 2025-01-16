package com.genaipeople.openai.assistant.thread.run;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.genaipeople.openai.assistant.DeserializerUtil;
import com.genaipeople.openai.assistant.thread.AdditionalMessage;

public class RunCreateRequestDeserializer extends StdDeserializer<RunCreateRequest> {
    
    public RunCreateRequestDeserializer() {
        this(null);
    }

    public RunCreateRequestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RunCreateRequest deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        RunCreateRequest request = new RunCreateRequest();
        
        request.setAssistantId(getTextValue(node, "assistant_id"));
        request.setModel(getTextValue(node, "model"));
        request.setInstructions(getTextValue(node, "instructions"));
        request.setAdditionalInstructions(getTextValue(node, "additional_instructions"));
        
        if (node.has("additional_messages")) {
            ObjectMapper mapper = (ObjectMapper) jp.getCodec();
            List<AdditionalMessage> messages = mapper.convertValue(
                node.get("additional_messages"),
                mapper.getTypeFactory().constructCollectionType(List.class, AdditionalMessage.class));
            request.setAdditionalMessages(messages);
        }

        // Handle tools array
        if (node.has("tools") && node.get("tools").isArray()) {
            request.setTools(DeserializerUtil.deserializeTools(jp, node));
        }

        // Handle metadata
        if (node.has("metadata")) {
            request.setMetadata(DeserializerUtil.deserializeMetadata(jp, node));
        }

        request.setTemperature(getDoubleValue(node, "temperature"));
        request.setTopP(getDoubleValue(node, "top_p"));
        request.setStream(getBooleanValue(node, "stream"));
        request.setMaxPromptTokens(getIntegerValue(node, "max_prompt_tokens"));
        request.setMaxCompletionTokens(getIntegerValue(node, "max_completion_tokens"));
        request.setParallelToolCalls(getBooleanValue(node, "parallel_tool_calls"));

        if (node.has("truncation_strategy")) {
            TruncationStrategy strategy = jp.getCodec().treeToValue(
                node.get("truncation_strategy"), 
                TruncationStrategy.class);
            request.setTruncationStrategy(strategy);
        }

        if (node.has("tool_choice")) {
            ToolChoice toolChoice = jp.getCodec().treeToValue(
                node.get("tool_choice"), 
                ToolChoice.class);
            request.setToolChoice(toolChoice);
        }

        return request;
    }

    private String getTextValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }

    private Double getDoubleValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asDouble() : null;
    }

    private Boolean getBooleanValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asBoolean() : null;
    }

    private Integer getIntegerValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asInt() : null;
    }
} 