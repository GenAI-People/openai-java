package com.genaipeople.openai.assistant.message;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.genaipeople.openai.assistant.Attachment;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = ThreadMessageObjectDeserializer.class)
public class ThreadMessageObject {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object = "thread.message";

    @JsonProperty("created_at")
    private Integer createdAt;

    @JsonProperty("thread_id")
    private String threadId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("incomplete_details")
    private IncompleteDetails incompleteDetails;

    @JsonProperty("completed_at")
    private Integer completedAt;

    @JsonProperty("incomplete_at")
    private Integer incompleteAt;

    @JsonProperty("role")
    private String role;

    @JsonProperty("content")
    private List<ThreadMessageContent> content;

    @JsonProperty("assistant_id")
    private String assistantId;

    @JsonProperty("run_id")
    private String runId;

    @JsonProperty("attachments")
    private List<Attachment> attachments;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getObject() { return object; }

    public Integer getCreatedAt() { return createdAt; }
    public void setCreatedAt(Integer createdAt) { this.createdAt = createdAt; }

    public String getThreadId() { return threadId; }
    public void setThreadId(String threadId) { this.threadId = threadId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public IncompleteDetails getIncompleteDetails() { return incompleteDetails; }
    public void setIncompleteDetails(IncompleteDetails incompleteDetails) { this.incompleteDetails = incompleteDetails; }

    public Integer getCompletedAt() { return completedAt; }
    public void setCompletedAt(Integer completedAt) { this.completedAt = completedAt; }

    public Integer getIncompleteAt() { return incompleteAt; }
    public void setIncompleteAt(Integer incompleteAt) { this.incompleteAt = incompleteAt; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<ThreadMessageContent> getContent() { return content; }
    public void setContent(List<ThreadMessageContent> content) { this.content = content; }

    public String getAssistantId() { return assistantId; }
    public void setAssistantId(String assistantId) { this.assistantId = assistantId; }

    public String getRunId() { return runId; }
    public void setRunId(String runId) { this.runId = runId; }

    public List<Attachment> getAttachments() { return attachments; }
    public void setAttachments(List<Attachment> attachments) { this.attachments = attachments; }

    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }
}
