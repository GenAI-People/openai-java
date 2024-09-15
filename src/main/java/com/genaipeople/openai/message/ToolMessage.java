package com.genaipeople.openai.message;

import com.genaipeople.openai.Role;

public class ToolMessage extends Message {
    private String toolCallId;

    public ToolMessage(String content, String toolCallId) {
        super(content, Role.TOOL);
        this.toolCallId = toolCallId;
    }

    // Getter
    public String getToolCallId() {
        return toolCallId;
    }

    // Setter
    public void setToolCallId(String toolCallId) {
        this.toolCallId = toolCallId;
    }
}
