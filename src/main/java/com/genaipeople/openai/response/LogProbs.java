package com.genaipeople.openai.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogProbs {
    @JsonProperty("content")
    private List<ContentToken> content;

    // Getter and setter
    public List<ContentToken> getContent() {
        return content;
    }

    public void setContent(List<ContentToken> content) {
        this.content = content;
    }
}