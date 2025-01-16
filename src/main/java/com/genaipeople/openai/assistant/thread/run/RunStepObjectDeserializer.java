package com.genaipeople.openai.assistant.thread.run;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.genaipeople.openai.response.Usage;

import java.io.IOException;
import java.util.Map;

public class RunStepObjectDeserializer extends JsonDeserializer<RunStepObject> {
    @Override
    public RunStepObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);
        
        RunStepObject runStep = new RunStepObject();
        
        if (node.has("id")) runStep.setId(node.get("id").asText());
        if (node.has("object")) runStep.setObject(node.get("object").asText());
        if (node.has("created_at")) runStep.setCreatedAt(node.get("created_at").asLong());
        if (node.has("assistant_id")) runStep.setAssistantId(node.get("assistant_id").asText());
        if (node.has("thread_id")) runStep.setThreadId(node.get("thread_id").asText());
        if (node.has("run_id")) runStep.setRunId(node.get("run_id").asText());
        if (node.has("type")) runStep.setType(node.get("type").asText());
        if (node.has("status")) runStep.setStatus(node.get("status").asText());
        
        // Handle step_details based on type
        if (node.has("step_details")) {
            JsonNode stepDetailsNode = node.get("step_details");
            ObjectMapper stepDetailsMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(StepDetails.class, new StepDetailsDeserializer());
            stepDetailsMapper.registerModule(module);
            
            StepDetails stepDetails = stepDetailsMapper.treeToValue(stepDetailsNode, StepDetails.class);
            runStep.setStepDetails(stepDetails);
        }
        
        if (node.has("last_error")) runStep.setLastError(mapper.treeToValue(node.get("last_error"), Error.class));
        if (node.has("expires_at")) runStep.setExpiresAt(node.get("expires_at").asLong());
        if (node.has("cancelled_at")) runStep.setCancelledAt(node.get("cancelled_at").asLong());
        if (node.has("failed_at")) runStep.setFailedAt(node.get("failed_at").asLong());
        if (node.has("completed_at")) runStep.setCompletedAt(node.get("completed_at").asLong());
        
        if (node.has("metadata")) {
            @SuppressWarnings("unchecked")
            Map<String,String> metadata = mapper.convertValue(node.get("metadata"), Map.class);
            runStep.setMetadata(metadata);
        }
        
        if (node.has("usage")) {
            runStep.setUsage(mapper.treeToValue(node.get("usage"), Usage.class));
        }
        
        return runStep;
    }
} 