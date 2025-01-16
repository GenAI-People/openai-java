package com.genaipeople.openai.assistant.thread.run;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageCreationStep extends StepDetails {
    @JsonProperty("message_id")
    private String messageId;

    public MessageCreationStep() {
        setType("message_creation");
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
