package com.genaipeople.openai.assistant.thread;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.tool.Tool;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunRequest {
    @JsonProperty("assistant_id")
    private String assistantId;

    @JsonProperty("model")
    private String model;

    @JsonProperty("instructions")
    private String instructions;

    @JsonProperty("additional_instructions")
    private String additionalInstructions;

    @JsonProperty("additional_messages")
    private List<AdditionalMessage> additionalMessages;

    @JsonProperty("tools")
    private List<Tool> tools;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("top_p")
    private Double topP;

    @JsonProperty("stream")
    private Boolean stream;

    @JsonProperty("max_prompt_tokens")
    private Integer maxPromptTokens;

    @JsonProperty("max_completion_tokens")
    private Integer maxCompletionTokens;

    @JsonProperty("truncation_strategy")
    private TruncationStrategy truncationStrategy;

    @JsonProperty("tool_choice")
    private ToolChoice toolChoice;

    @JsonProperty("parallel_tool_calls")
    private Boolean parallelToolCalls;


    // Required assistantId constructor
    public RunRequest(String assistantId) {
        if (assistantId == null || assistantId.trim().isEmpty()) {
            throw new IllegalArgumentException("assistant_id is required");
        }
        this.assistantId = assistantId;
    }

    // Getters and setters with validation
    public String getAssistantId() {
        return assistantId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getAdditionalInstructions() {
        return additionalInstructions;
    }

    public void setAdditionalInstructions(String additionalInstructions) {
        this.additionalInstructions = additionalInstructions;
    }

    public Map<String, String> getMetadata() {
        return metadata;
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

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        if (temperature != null && (temperature < 0 || temperature > 2)) {
            throw new IllegalArgumentException("Temperature must be between 0 and 2");
        }
        this.temperature = temperature;
    }

    public Double getTopP() {
        return topP;
    }

    public void setTopP(Double topP) {
        if (topP != null && (topP < 0 || topP > 1)) {
            throw new IllegalArgumentException("Top P must be between 0 and 1");
        }
        this.topP = topP;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public Integer getMaxPromptTokens() {
        return maxPromptTokens;
    }

    public void setMaxPromptTokens(Integer maxPromptTokens) {
        if (maxPromptTokens != null && maxPromptTokens <= 0) {
            throw new IllegalArgumentException("Max prompt tokens must be positive");
        }
        this.maxPromptTokens = maxPromptTokens;
    }

    public Integer getMaxCompletionTokens() {
        return maxCompletionTokens;
    }

    public void setMaxCompletionTokens(Integer maxCompletionTokens) {
        if (maxCompletionTokens != null && maxCompletionTokens <= 0) {
            throw new IllegalArgumentException("Max completion tokens must be positive");
        }
        this.maxCompletionTokens = maxCompletionTokens;
    }

    public List<AdditionalMessage> getAdditionalMessages() {
        return additionalMessages;
    }

    public void setAdditionalMessages(List<AdditionalMessage> additionalMessages) {
        this.additionalMessages = additionalMessages;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public Boolean getParallelToolCalls() {
        return parallelToolCalls;
    }

    public void setParallelToolCalls(Boolean parallelToolCalls) {
        this.parallelToolCalls = parallelToolCalls;
    }

    public TruncationStrategy getTruncationStrategy() {
        return truncationStrategy;
    }

    public void setTruncationStrategy(TruncationStrategy truncationStrategy) {
        this.truncationStrategy = truncationStrategy;
    }

    public ToolChoice getToolChoice() {
        return toolChoice;
    }

    public void setToolChoice(ToolChoice toolChoice) {
        this.toolChoice = toolChoice;
    }


    /*
     * Controls for how a thread will be truncated prior to the run. Use this to control the intial context window of the run.
     * Hide properties
     * type
     * string

     * Required
     * The truncation strategy to use for the thread. The default is auto. If set to last_messages, the thread will be truncated to the n most recent messages in the thread. When set to auto, messages in the middle of the thread will be dropped to fit the context length of the model, max_prompt_tokens.

     * last_messages
     * integer or null
     * Optional
     * The number of most recent messages from the thread when constructing the context for the run.
     */
    public class TruncationStrategy {
        @JsonProperty("type")
        private String type;

        @JsonProperty("last_messages")
        private Integer lastMessages;

        public TruncationStrategy(TruncationStrategyType type) {
            this.type = type.getValue();
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public Integer getLastMessages() { return lastMessages; }
        public void setLastMessages(Integer lastMessages) { this.lastMessages = lastMessages; }
    }

    public class ToolChoice {
        @JsonProperty("type")
        private String type;

        @JsonProperty("object")
        private Tool tool;

       public String getType() { return type; }
       public void setType(String type) { this.type = type; }

       public Tool getTool() { return tool; }
       public void setTool(Tool tool) { this.tool = tool; }
    }

    public enum TruncationStrategyType {
        AUTO("auto"),
        LAST_MESSAGES("last_messages");

        private final String value;

        TruncationStrategyType(String value) { this.value = value; }
        public String getValue() { return value; }
    }

    public enum ToolChoiceType {
        none("none"),
        AUTO("auto"),
        REQUIRED("required");

        private final String value;

        ToolChoiceType(String value) { this.value = value; }
        public String getValue() { return value; }
    }
}
