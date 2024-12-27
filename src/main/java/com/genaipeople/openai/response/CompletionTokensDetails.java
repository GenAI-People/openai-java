package com.genaipeople.openai.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompletionTokensDetails {
    @JsonProperty("reasoning_tokens")
    private Integer reasoningTokens;

    @JsonProperty("audio_tokens")
    private Integer audioTokens;

    @JsonProperty("accepted_prediction_tokens")
    private Integer acceptedPredictionTokens;

    @JsonProperty("rejected_prediction_tokens")
    private Integer rejectedPredictionTokens;

    public Integer getReasoningTokens() {
        return reasoningTokens;
    }

    public void setReasoningTokens(Integer reasoningTokens) {
        this.reasoningTokens = reasoningTokens;
    }

    public Integer getAudioTokens() {
        return audioTokens;
    }

    public void setAudioTokens(Integer audioTokens) {
        this.audioTokens = audioTokens;
    }

    public Integer getAcceptedPredictionTokens() {
        return acceptedPredictionTokens;
    }

    public void setAcceptedPredictionTokens(Integer acceptedPredictionTokens) {
        this.acceptedPredictionTokens = acceptedPredictionTokens;
    }

    public Integer getRejectedPredictionTokens() {
        return rejectedPredictionTokens;
    }

    public void setRejectedPredictionTokens(Integer rejectedPredictionTokens) {
        this.rejectedPredictionTokens = rejectedPredictionTokens;
    }
}
