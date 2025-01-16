package com.genaipeople.openai.assistant;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.tool.Tool;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssistantRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("instructions")
    private String instructions;

    @JsonProperty("tools")
    private List<Tool> tools;

    @JsonProperty("model")
    private String model;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    public AssistantRequest() {}

    public AssistantRequest(String instructions, String name, List<Tool> tools, String model) {
        this.instructions = instructions;
        this.name = name;
        this.tools = tools;
        this.model = model;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public List<Tool> getTools() { return tools; }
    public void setTools(List<Tool> tools) { this.tools = tools; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }
} 