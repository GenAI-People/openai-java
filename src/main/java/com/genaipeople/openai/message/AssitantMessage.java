package com.genaipeople.openai.message;

import java.util.List;

import com.genaipeople.openai.Role;
import com.genaipeople.openai.assistant.ToolCall;

public class AssitantMessage extends Message {
    private String refusal;
    private List<ToolCall> toolCalls;
    
    public AssitantMessage(){
        super(null, Role.ASSISTANT);
    }

    public AssitantMessage(String content){
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
