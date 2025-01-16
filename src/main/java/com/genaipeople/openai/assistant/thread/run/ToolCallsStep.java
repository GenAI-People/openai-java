package com.genaipeople.openai.assistant.thread.run;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.response.ToolCall;

public class ToolCallsStep extends StepDetails {
    @JsonProperty("tool_calls")
    private List<ToolCall> toolCalls;

    public ToolCallsStep() {
        setType("tool_calls");
    }

    public List<ToolCall> getToolCalls() {
        return toolCalls;
    }

    public void setToolCalls(List<ToolCall> toolCalls) {
        this.toolCalls = toolCalls;
    }
} 