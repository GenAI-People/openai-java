package com.genaipeople.openai.assistant.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IncompleteDetails {
    @JsonProperty("reason")
    private String reason;

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
