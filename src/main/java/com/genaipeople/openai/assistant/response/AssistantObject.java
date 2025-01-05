package com.genaipeople.openai.assistant.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.genaipeople.openai.assistant.ToolResource;
import com.genaipeople.openai.tool.Tool;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = AssistantObjectDeserializer.class)
public class AssistantObject {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

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

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("top_p")
    private Double topP;

    @JsonProperty("response_format")
    private ResponseFormat responseFormat;

    // Getters and Setters with validation
    public void setName(String name) {
        if (name != null && name.length() > 256) {
            throw new IllegalArgumentException("Name cannot exceed 256 characters");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if (description != null && description.length() > 512) {
            throw new IllegalArgumentException("Description cannot exceed 512 characters");
        }
        this.description = description;
    }

    public void setInstructions(String instructions) {
        if (instructions != null && instructions.length() > 256000) {
            throw new IllegalArgumentException("Instructions cannot exceed 256,000 characters");
        }
        this.instructions = instructions;
    }

    public void setTools(List<Tool> tools) {
        if (tools != null && tools.size() > 128) {
            throw new IllegalArgumentException("Maximum of 128 tools allowed");
        }
        this.tools = tools;
    }

    public void setMetadata(Map<String, String> metadata) {
        if (metadata != null) {
            metadata.forEach((key, value) -> {
                if (key.length() > 64) {
                    throw new IllegalArgumentException("Metadata key cannot exceed 64 characters");
                }
                if (value.length() > 512) {
                    throw new IllegalArgumentException("Metadata value cannot exceed 512 characters");
                }
            });
        }
        this.metadata = metadata;
    }

    public void setTemperature(Double temperature) {
        if (temperature != null && (temperature < 0 || temperature > 2)) {
            throw new IllegalArgumentException("Temperature must be between 0 and 2");
        }
        this.temperature = temperature;
    }

    // Standard getters
    public String getId() { return id; }
    public String getObject() { return object; }
    public Long getCreatedAt() { return createdAt; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getModel() { return model; }
    public String getInstructions() { return instructions; }
    public List<Tool> getTools() { return tools; }
    public ToolResource getToolResources() { return toolResources; }
    public Map<String, String> getMetadata() { return metadata; }
    public Double getTemperature() { return temperature; }
    public Double getTopP() { return topP; }
    public ResponseFormat getResponseFormat() { return responseFormat; }

    // Remaining setters
    public void setId(String id) { this.id = id; }
    public void setObject(String object) { this.object = object; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
    public void setModel(String model) { this.model = model; }
    public void setToolResources(ToolResource toolResources) { this.toolResources = toolResources; }
    public void setTopP(Double topP) { this.topP = topP; }
    public void setResponseFormat(ResponseFormat responseFormat) { this.responseFormat = responseFormat; }
}
