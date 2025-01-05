package com.genaipeople.openai;

import java.util.concurrent.CompletableFuture;
import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.assistant.AssistantRequest;
import com.genaipeople.openai.assistant.response.AssistantObject;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;

public class Assistant {
    private final String apiKey;
    private final String ASSISTANT_URL = "https://api.openai.com/v1/assistants";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> headers = new HashMap<>();        
    public Assistant(String apiKey) {
        this.apiKey = apiKey;
        headers.put("OpenAI-Beta", "assistants=v2");
    }

    public CompletableFuture<AssistantObject> create(AssistantRequest request) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, ASSISTANT_URL, HttpMethod.POST, request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, AssistantObject.class, objectMapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<AssistantObject> retrieve(String assistantId) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, ASSISTANT_URL + "/" + assistantId, HttpMethod.GET, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, AssistantObject.class, objectMapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<AssistantObject> update(String assistantId, AssistantRequest request) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, ASSISTANT_URL + "/" + assistantId, HttpMethod.POST, request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, AssistantObject.class, objectMapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<AssistantObject> delete(String assistantId) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, ASSISTANT_URL + "/" + assistantId, HttpMethod.DELETE, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, AssistantObject.class, objectMapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<AssistantObject> list(Integer limit, String order, String after, String before) {
        Map<String, String> queryParams = new HashMap<>();
        if (limit != null) {
            queryParams.put("limit", limit.toString());
        }
        if (order != null) {
            queryParams.put("order", order);
        }
        if (after != null) {
            queryParams.put("after", after);
        }
        if (before != null) {
            queryParams.put("before", before);
        }
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, ASSISTANT_URL, HttpMethod.GET, null, headers, queryParams)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, AssistantObject.class, objectMapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }
}