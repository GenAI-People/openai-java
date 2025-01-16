package com.genaipeople.openai.assistant.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThreadRefusalMessageContent extends ThreadMessageContent {
    @JsonProperty("refusal")
    private String refusal;

    public ThreadRefusalMessageContent(String refusal) {
        super("refusal");
        this.refusal = refusal;
    }

    public String getRefusal() { return refusal; }
    public void setRefusal(String refusal) { this.refusal = refusal; }
}
