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

    @JsonProperty("audio_tokens")
    public int getAudioTokens() {
        return audioTokens;
    }

    @JsonProperty("audio_tokens")
    public void setAudioTokens(int audioTokens) {
        this.audioTokens = audioTokens;
    }

    @JsonProperty("accepted_prediction_tokens")
    public int getAcceptedPredictionTokens() {
        return acceptedPredictionTokens;
    }

    @JsonProperty("accepted_prediction_tokens")
    public void setAcceptedPredictionTokens(int acceptedPredictionTokens) {
        this.acceptedPredictionTokens = acceptedPredictionTokens;
    }

    @JsonProperty("rejected_prediction_tokens")
    public int getRejectedPredictionTokens() {
        return rejectedPredictionTokens;
    }

    @JsonProperty("rejected_prediction_tokens")
    public void setRejectedPredictionTokens(int rejectedPredictionTokens) {
        this.rejectedPredictionTokens = rejectedPredictionTokens;
    }
}
