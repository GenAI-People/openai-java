package com.genaipeople.openai;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;
import com.genaipeople.openai.text.ChatRequest;
import com.genaipeople.openai.text.ChatResponse;
import com.genaipeople.openai.text.ErrorResponse;

public class Chat {
    private final String apiKey;
    private final String COMPLETION_URL = "https://api.openai.com/v1/chat/completions";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Pattern ERROR_PATTERN = Pattern.compile("^\\s*\\{\\s*\"error\":\\s*\\{");

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
                return CompletableFuture.failedFuture(new RuntimeException("Request interrupted", e));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(new RuntimeException(e.getMessage()));
            }
        });
    }

    public ChatResponse stringToChatResponse(String responseString) {
        try {
            if (ERROR_PATTERN.matcher(responseString).find()) {
                String jsonPart = responseString.substring(responseString.indexOf("{"));
                ErrorResponse errorResponse = objectMapper.readValue(jsonPart, ErrorResponse.class);
                throw new RuntimeException("Code: " + errorResponse.getError().getCode() + 
                    " Message: " + errorResponse.getError().getMessage());
            }
            return objectMapper.readValue(responseString, ChatResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing response string to ChatResponse", e);
        }
    }
}