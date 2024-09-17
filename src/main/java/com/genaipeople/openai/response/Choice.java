package com.genaipeople.openai.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.message.Message;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Choice {
    @JsonProperty("index")
    private Integer index;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("finish_reason")
    private String finishReason;

    @JsonProperty("logprobs")
    private LogProbs logprobs;

    // Getters
    public Integer getIndex() {
        return index;
    }

    public Message getMessage() {
        return message;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public LogProbs getLogprobs() {
        return logprobs;
    }

    // Setters
    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public void setLogprobs(LogProbs logprobs) {
        this.logprobs = logprobs;
    }
}