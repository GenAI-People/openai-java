package com.genaipeople.openai;

import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;
import com.genaipeople.openai.text.ChatRequest;
import com.genaipeople.openai.text.ChatResponse;

public class Chat {
    private final String apiKey;
    private final String COMPLETION_URL = "https://api.openai.com/v1/chat/completions";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Chat(String apiKey){
        this.apiKey = apiKey;
    }

    public CompletableFuture<ChatResponse> complete(ChatRequest request) {
        return CompletableFuture.supplyAsync(() -> 
            RestClient.makeAsyncRequest(apiKey, COMPLETION_URL, HttpMethod.POST, request)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                System.out.println("Response: " + responseString);
                return CompletableFuture.completedFuture(stringToChatResponse(responseString));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return CompletableFuture.failedFuture(new RuntimeException("Request interrupted", e));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(new RuntimeException("Error executing request", e));
            }
        }).exceptionally(e -> {
            System.err.println("Error in chat completion: " + e.getMessage());
            throw new RuntimeException("Error executing request", e);
        });
    }

    public ChatResponse stringToChatResponse(String responseString) {
        try {
            return objectMapper.readValue(responseString, ChatResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing response string to ChatResponse", e);
        }
    }
}