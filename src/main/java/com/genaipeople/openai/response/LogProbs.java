package com.genaipeople.openai.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogProbs {
    @JsonProperty("content")
    private List<ContentToken> content;
    @JsonProperty("top_logprobs")
    private List<ContentToken> topLogprobs;
    @JsonProperty("refusal")    
    private String refusal;
    // Getter and setter
    public List<ContentToken> getContent() {
        return content;
    }

    public void setContent(List<ContentToken> content) {
        this.content = content;
    }

    public String getRefusal() {
        return refusal;
    }

    public void setRefusal(String refusal) {
        this.refusal = refusal;
    }

    public List<ContentToken> getTopLogprobs() {
        return topLogprobs;
    }

    public void setTopLogprobs(List<ContentToken> topLogprobs) {
        this.topLogprobs = topLogprobs;
    }
}
