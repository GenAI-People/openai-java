package com.genaipeople.openai.assistant.thread.run;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.genaipeople.openai.assistant.ToolResource;
import com.genaipeople.openai.response.Usage;
import com.genaipeople.openai.tool.Tool;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = RunObjectDeserializer.class)
public class RunObject {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("assistant_id")
    private String assistantId;

    @JsonProperty("thread_id")
    private String threadId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("started_at")
    private Long startedAt;

    @JsonProperty("expires_at")
    private Long expiresAt;

    @JsonProperty("cancelled_at")
    private Long cancelledAt;

    @JsonProperty("failed_at")
    private Long failedAt;

    @JsonProperty("completed_at")
    private Long completedAt;

    @JsonProperty("last_error")
    private Error lastError;

    @JsonProperty("model")
    private String model;

    @JsonProperty("instructions")
    private String instructions;

    @JsonProperty("tools")
    private List<Tool> tools;

    @JsonProperty("tool_resources")
    private ToolResource toolResources;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    @JsonProperty("usage")
    private Usage usage;

    @JsonProperty("required_action")
    private RequiredAction requiredAction;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    public String getAssistantId() { return assistantId; }
    public void setAssistantId(String assistantId) { this.assistantId = assistantId; }

    public String getThreadId() { return threadId; }
    public void setThreadId(String threadId) { this.threadId = threadId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getStartedAt() { return startedAt; }
    public void setStartedAt(Long startedAt) { this.startedAt = startedAt; }

    public Long getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Long expiresAt) { this.expiresAt = expiresAt; }

    public Long getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(Long cancelledAt) { this.cancelledAt = cancelledAt; }

    public Long getFailedAt() { return failedAt; }
    public void setFailedAt(Long failedAt) { this.failedAt = failedAt; }

    public Long getCompletedAt() { return completedAt; }
    public void setCompletedAt(Long completedAt) { this.completedAt = completedAt; }

    public Error getLastError() { return lastError; }
    public void setLastError(Error lastError) { this.lastError = lastError; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public List<Tool> getTools() { return tools; }
    public void setTools(List<Tool> tools) { this.tools = tools; }

    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }

    public Usage getUsage() { return usage; }
    public void setUsage(Usage usage) { this.usage = usage; }

    public RequiredAction getRequiredAction() { return requiredAction; }
    public void setRequiredAction(RequiredAction requiredAction) { this.requiredAction = requiredAction; }

    public ToolResource getToolResources() { return toolResources; }
    public void setToolResources(ToolResource toolResources) { this.toolResources = toolResources; }
} 
