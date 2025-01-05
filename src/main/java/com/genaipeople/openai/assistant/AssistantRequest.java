package com.genaipeople.openai.assistant;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.assistant.response.ResponseFormat;
import com.genaipeople.openai.tool.CodeInterpreter;
import com.genaipeople.openai.tool.Tool;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssistantRequest {
    @JsonProperty("model")
    private String model;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
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

    public AssistantRequest() {}// Main class constructor
    public AssistantRequest(String instructions, String name, 
        List<Tool> tools, String model) {
        this.instructions = instructions;
        this.name = name;
        this.tools = tools;
        this.model = model;
    }
    // Main class getters and setters
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getName() { return name; }

    // The name of the assistant. The maximum length is 256 characters.
    public void setName(String name) {
        if (name.length() > 256) {
            throw new IllegalArgumentException("Name must be less than 256 characters");
        }
        this.name = name;
    }
    public String getDescription() { return description; }

    // The description of the assistant. The maximum length is 512 characters.
    public void setDescription(String description) {
        if (description.length() > 512) {
            throw new IllegalArgumentException("Description must be less than 512 characters");
        }
        this.description = description;
    }
    public String getInstructions() { return instructions; }

    // The instructions that the assistant uses to accomplish tasks. The maximum length is 256,000 characters.
    public void setInstructions(String instructions) {
        if (instructions.length() > 256000) {
            throw new IllegalArgumentException("Instructions must be less than 256000 characters");
        }
        this.instructions = instructions;
    }
    public List<Tool> getTools() { return tools; }

    /*
     * A list of tool enabled on the assistant. There can be a maximum of 128 tools per assistant. 
     * Tools can be of types code_interpreter, file_search, or function.
     */
    public void setTools(List<Tool> tools) {
        if (tools.size() > 128) {
            throw new IllegalArgumentException("Tools must be less than 128");
        }
        this.tools = tools;
    }
    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }
    public ToolResource getToolResources() { return toolResources; }
    public void setToolResources(ToolResource toolResources) { this.toolResources = toolResources; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getTopP() { return topP; }
    public void setTopP(Double topP) { this.topP = topP; }
    public ResponseFormat getResponseFormat() { return responseFormat; }
    public void setResponseFormat(ResponseFormat responseFormat) { this.responseFormat = responseFormat; }
} 