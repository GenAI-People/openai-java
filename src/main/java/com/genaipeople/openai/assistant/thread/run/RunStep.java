package com.genaipeople.openai.assistant.thread.run;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.response.Usage;
import com.genaipeople.openai.text.ErrorResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunStep {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("run_id") 
    private String runId;

    @JsonProperty("assistant_id")
    private String assistantId;

    @JsonProperty("thread_id")
    private String threadId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("status")
    private String status;

    @JsonProperty("cancelled_at")
    private Long cancelledAt;

    @JsonProperty("completed_at")
    private Long completedAt;

    @JsonProperty("expired_at")
    private Long expiredAt;

    @JsonProperty("failed_at")
    private Long failedAt;

    @JsonProperty("last_error")
    private ErrorResponse.Error lastError;

    @JsonProperty("step_details")
    private StepDetails stepDetails;

    @JsonProperty("usage")
    private Usage usage;

    @JsonProperty("metadata")
    private Map<String, String> metadata = new HashMap<>();

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    public String getRunId() { return runId; }
    public void setRunId(String runId) { this.runId = runId; }

    public String getAssistantId() { return assistantId; }
    public void setAssistantId(String assistantId) { this.assistantId = assistantId; }

    public String getThreadId() { return threadId; }
    public void setThreadId(String threadId) { this.threadId = threadId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(Long cancelledAt) { this.cancelledAt = cancelledAt; }

    public Long getCompletedAt() { return completedAt; }
    public void setCompletedAt(Long completedAt) { this.completedAt = completedAt; }

    public Long getExpiredAt() { return expiredAt; }
    public void setExpiredAt(Long expiredAt) { this.expiredAt = expiredAt; }

    public Long getFailedAt() { return failedAt; }
    public void setFailedAt(Long failedAt) { this.failedAt = failedAt; }

    public ErrorResponse.Error getLastError() { return lastError; }
    public void setLastError(ErrorResponse.Error lastError) { this.lastError = lastError; }

    public StepDetails getStepDetails() { return stepDetails; }
    public void setStepDetails(StepDetails stepDetails) { this.stepDetails = stepDetails; }

    public Usage getUsage() { return usage; }
    public void setUsage(Usage usage) { this.usage = usage; }

    public Map<String, String> getMetadata() { return metadata; }

    // Set of 16 key-value pairs that can be attached to an object. 
    // This can be useful for storing additional information about the object in a structured format. 
    // Keys can be a maximum of 64 characters long and values can be a maximum of 512 characters long.
    public void setMetadata(Map<String, String> metadata) { 
        if (metadata.size() > 16) {
            throw new IllegalArgumentException("Metadata can only contain up to 16 key-value pairs.");
        }
        for (Map.Entry<String, String> entry : metadata.entrySet()) {
            if (entry.getKey().length() > 64 || entry.getValue().length() > 512) {
                throw new IllegalArgumentException("Key or value in metadata is too long.");
            }
        }
        this.metadata = metadata; 
    }
}