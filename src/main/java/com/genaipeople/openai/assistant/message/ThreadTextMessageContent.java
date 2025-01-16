package com.genaipeople.openai.assistant.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThreadTextMessageContent extends ThreadMessageContent {
    @JsonProperty("text")
    private String text;


    public ThreadTextMessageContent(String text) {
        super("text");
        this.text = text;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
