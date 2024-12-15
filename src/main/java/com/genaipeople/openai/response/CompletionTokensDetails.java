package com.genaipeople.openai.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompletionTokensDetails {
    @JsonProperty("reasoning_tokens")
    private Integer reasoningTokens;

    @JsonProperty("audio_tokens")
    private int audioTokens;

    @JsonProperty("accepted_prediction_tokens")
    private int acceptedPredictionTokens;

    @JsonProperty("rejected_prediction_tokens")
    private int rejectedPredictionTokens;


    public Integer getReasoningTokens() {
        return reasoningTokens;
    }

    public void setReasoningTokens(Integer reasoningTokens) {
        this.reasoningTokens = reasoningTokens;
    }

    public int getAudioTokens() {
        return audioTokens;
    }

    public void setAudioTokens(int audioTokens) {
        this.audioTokens = audioTokens;
    }

    public int getAcceptedPredictionTokens() {
        return acceptedPredictionTokens;
    }

    public void setAcceptedPredictionTokens(int acceptedPredictionTokens) {
        this.acceptedPredictionTokens = acceptedPredictionTokens;
    }

    public int getRejectedPredictionTokens() {
        return rejectedPredictionTokens;
    }

    public void setRejectedPredictionTokens(int rejectedPredictionTokens) {
        this.rejectedPredictionTokens = rejectedPredictionTokens;
    }
}