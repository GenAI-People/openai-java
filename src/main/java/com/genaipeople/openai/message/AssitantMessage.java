package com.genaipeople.openai.message;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.Role;
import com.genaipeople.openai.assistant.ToolCall;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssitantMessage extends Message {
    @JsonProperty("refusal")
    private String refusal;

    @JsonProperty("tool_calls")
    private List<ToolCall> toolCalls;
    
    public AssitantMessage() {
        super(null, Role.ASSISTANT);
    }

    public AssitantMessage(String content) {
        super(content, Role.ASSISTANT);
    }

    // Getters
    public String getRefusal() {
        return refusal;
    }

    public List<ToolCall> getToolCalls() {
        return toolCalls;
    }

    // Setters
    public void setRefusal(String refusal) {
        this.refusal = refusal;
    }

    public void setToolCalls(List<ToolCall> toolCalls) {
        this.toolCalls = toolCalls;
    }
}
