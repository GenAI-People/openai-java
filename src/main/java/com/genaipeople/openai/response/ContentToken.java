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

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getLogprob() {
        return logprob;
    }

    public void setLogprob(double logprob) {
        this.logprob = logprob;
    }

    public List<Integer> getBytes() {
        return bytes;
    }

    public void setBytes(List<Integer> bytes) {
        this.bytes = bytes;
    }

    public List<ContentToken> getTopLogprobs() {
        return topLogprobs;
    }

    public void setTopLogprobs(List<ContentToken> topLogprobs) {
        this.topLogprobs = topLogprobs;
    }
}