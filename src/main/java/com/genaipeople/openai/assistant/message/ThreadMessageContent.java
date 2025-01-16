package com.genaipeople.openai.assistant.message;

public abstract class ThreadMessageContent {
    private String type;

    protected ThreadMessageContent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
