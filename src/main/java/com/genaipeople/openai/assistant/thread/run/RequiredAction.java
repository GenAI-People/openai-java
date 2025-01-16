package com.genaipeople.openai.assistant.thread.run;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.response.ToolCall;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequiredAction {
    @JsonProperty("type")
    private String type;

    @JsonProperty("submit_tool_outputs")
    private SubmitToolOutputs submitToolOutputs;

    public class SubmitToolOutputs {
        @JsonProperty("tool_calls")
        private List<ToolCall> toolCalls;

        public List<ToolCall> getToolCalls() { return toolCalls; }
        public void setToolCalls(List<ToolCall> toolCalls) { this.toolCalls = toolCalls; }
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public SubmitToolOutputs getSubmitToolOutputs() { return submitToolOutputs; }
    public void setSubmitToolOutputs(SubmitToolOutputs submitToolOutputs) { this.submitToolOutputs = submitToolOutputs; }
} 