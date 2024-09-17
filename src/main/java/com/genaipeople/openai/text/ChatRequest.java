package com.genaipeople.openai.text;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.message.Message;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRequest {
    @JsonProperty("messages")
    private List<Message> messages;

    @JsonProperty("model")
    private String model;

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("n")
    private Integer n;

    @JsonProperty("stream")
    private Boolean stream;

    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;

    @JsonProperty("logit_bias")
    private Map<String, Integer> logitBias;

    @JsonProperty("logprobs")
    private Boolean logprobs;

    @JsonProperty("top_logprobs")
    private Integer topLogprobs;

    @JsonProperty("max_completion_tokens")
    private Integer maxCompletionTokens;

    @JsonProperty("presence_penalty")
    private Double presencePenalty;

    @JsonProperty("response_format")
    private ResponseFormat responseFormat;

    @JsonProperty("seed")
    private Integer seed;

    @JsonProperty("service_tier")
    private ServiceTier serviceTier;

    @JsonProperty("stop")
    private Object stop;

    @JsonProperty("stream_options")
    private Object streamOptions;

    @JsonProperty("top_p")
    private Double topP;

    @JsonProperty("tools")
    private List<Object> tools;

    @JsonProperty("tool_choice")
    private Object toolChoice;

    @JsonProperty("parallel_tool_calls")
    private Boolean parallelToolCalls;

    @JsonProperty("user")
    private String user;

    public ChatRequest(List<Message> messages, String model){
        this.messages = messages;
        this.model = model;
    }

    // Getters and setters for all properties
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public Double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public void setFrequencyPenalty(Double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public Map<String, Integer> getLogitBias() {
        return logitBias;
    }

    public void setLogitBias(Map<String, Integer> logitBias) {
        this.logitBias = logitBias;
    }

    public Boolean getLogprobs() {
        return logprobs;
    }

    public void setLogprobs(Boolean logprobs) {
        this.logprobs = logprobs;
    }

    public Integer getTopLogprobs() {
        return topLogprobs;
    }

    public void setTopLogprobs(Integer topLogprobs) {
        this.topLogprobs = topLogprobs;
    }

    public Integer getMaxCompletionTokens() {
        return maxCompletionTokens;
    }

    public void setMaxCompletionTokens(Integer maxCompletionTokens) {
        this.maxCompletionTokens = maxCompletionTokens;
    }

    public Double getPresencePenalty() {
        return presencePenalty;
    }

    public void setPresencePenalty(Double presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    public ResponseFormat getResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(ResponseFormat responseFormat) {
        this.responseFormat = responseFormat;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public ServiceTier getServiceTier() {
        return serviceTier;
    }

    public void setServiceTier(ServiceTier serviceTier) {
        this.serviceTier = serviceTier;
    }

    public Object getStop() {
        return stop;
    }

    public void setStop(Object stop) {
        this.stop = stop;
    }

    public Object getStreamOptions() {
        return streamOptions;
    }

    public void setStreamOptions(Object streamOptions) {
        this.streamOptions = streamOptions;
    }

    public Double getTopP() {
        return topP;
    }

    public void setTopP(Double topP) {
        this.topP = topP;
    }

    public List<Object> getTools() {
        return tools;
    }

    public void setTools(List<Object> tools) {
        this.tools = tools;
    }

    public Object getToolChoice() {
        return toolChoice;
    }

    public void setToolChoice(Object toolChoice) {
        this.toolChoice = toolChoice;
    }

    public Boolean getParallelToolCalls() {
        return parallelToolCalls;
    }

    public void setParallelToolCalls(Boolean parallelToolCalls) {
        this.parallelToolCalls = parallelToolCalls;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}