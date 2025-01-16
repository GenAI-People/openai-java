package com.genaipeople.openai.assistant.thread.run;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
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