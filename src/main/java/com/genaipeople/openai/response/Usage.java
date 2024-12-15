package com.genaipeople.openai.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usage {
    @JsonProperty("prompt_tokens")
    private Integer promptTokens;

    @JsonProperty("completion_tokens")
    private Integer completionTokens;

    @JsonProperty("total_tokens")
    private Integer totalTokens;

    @JsonProperty("completion_tokens_details")
    private CompletionTokensDetails completionTokensDetails;

    @JsonProperty("prompt_tokens_details")
    private PromptTokenDetails promptTokenDetails;

    @JsonProperty("audio_tokens")
    private int audioTokens;

    @JsonProperty("accepted_prediction_tokens")
    private int acceptedPredictionTokens;

    @JsonProperty("rejected_prediction_tokens")
    private int rejectedPredictionTokens;

    // Getters and setters
    public Integer getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(Integer promptTokens) {
        this.promptTokens = promptTokens;
    }

    public Integer getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(Integer completionTokens) {
        this.completionTokens = completionTokens;
    }

    public Integer getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(Integer totalTokens) {
        this.totalTokens = totalTokens;
    }

    public CompletionTokensDetails getCompletionTokensDetails() {
        return completionTokensDetails;
    }

    public void setCompletionTokensDetails(CompletionTokensDetails completionTokensDetails) {
        this.completionTokensDetails = completionTokensDetails;
    }

    public PromptTokenDetails getPromptTokenDetails() {
        return promptTokenDetails;
    }

    public void setPromptTokenDetails(PromptTokenDetails promptTokenDetails) {
        this.promptTokenDetails = promptTokenDetails;
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
