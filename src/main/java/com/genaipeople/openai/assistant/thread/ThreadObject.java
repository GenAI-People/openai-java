package com.genaipeople.openai.assistant.thread;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.genaipeople.openai.assistant.ToolResource;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = ThreadObjectDeserializer.class)
public class ThreadObject {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    @JsonProperty("tool_resources")
    private ToolResource toolResources;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public ToolResource getToolResources() {
        return toolResources;
    }

    public void setToolResources(ToolResource toolResources) {
        this.toolResources = toolResources;
    }
}
