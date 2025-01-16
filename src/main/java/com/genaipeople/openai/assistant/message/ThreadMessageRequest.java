package com.genaipeople.openai.assistant.message;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.Role;
import com.genaipeople.openai.assistant.Attachment;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ThreadMessageRequestSerializer.class)
@JsonDeserialize(using = ThreadMessageRequestDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThreadMessageRequest {
    @JsonProperty("role")
    private Role role;

    @JsonProperty("content")
    private List<ThreadMessageContent> content;

    @JsonProperty("attachments")
    private List<Attachment> attachments;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    // Constructor with required fields
    public ThreadMessageRequest(Role role, String content) {
        if (role == null) {
            throw new IllegalArgumentException("role is required");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("content is required");
        }
        this.role = role;
        this.content = Arrays.asList(new ThreadTextMessageContent(content));
    }

    public ThreadMessageRequest(Role role, List<ThreadMessageContent> content) {
        if (role == null) {
            throw new IllegalArgumentException("role is required");
        }
        this.role = role;
        this.content = content;
    }

    // Getters and setters
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public List<ThreadMessageContent> getContent() { return content; }
    public void setContent(List<ThreadMessageContent> content) { this.content = content; }

    public List<Attachment> getAttachments() { return attachments; }
    public void setAttachments(List<Attachment> attachments) { this.attachments = attachments; }

    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }
}
