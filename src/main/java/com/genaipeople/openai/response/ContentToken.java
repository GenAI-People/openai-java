package com.genaipeople.openai.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentToken {
    @JsonProperty("token")
    private String token;
    
    @JsonProperty("logprob")
    private double logprob;
    
    @JsonProperty("bytes")
    private List<Integer> bytes;
    
    @JsonProperty("top_logprobs")
    private List<ContentToken> topLogprobs;

    public ContentToken() {}

    public ContentToken(String token, double logprob, List<Integer> bytes) {
        this.token = token;
        this.logprob = logprob;
        this.bytes = bytes;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public double getLogprob() {
        return logprob;
    }

    public List<Integer> getBytes() {
        return bytes;
    }

    public List<ContentToken> getTopLogprobs() {
        return topLogprobs;
    }

    public void setTopLogprobs(List<ContentToken> topLogprobs) {
        this.topLogprobs = topLogprobs;
    }
}
