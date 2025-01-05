package com.genaipeople.openai;

import java.util.concurrent.CompletableFuture;
import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.assistant.thread.ThreadDeleteResponse;
import com.genaipeople.openai.assistant.thread.ThreadObject;
import com.genaipeople.openai.assistant.thread.ThreadRequest;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;

public class ExecutionThread {
    private final String apiKey;
    private final String THREAD_URL = "https://api.openai.com/v1/threads";
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, String> headers = new HashMap<>();

    public ExecutionThread(String apiKey) {
        this.apiKey = apiKey;
        headers.put("OpenAI-Beta", "assistants=v2");
    }

    public CompletableFuture<ThreadObject> create() {
        return create(new ThreadRequest());
    }

    public CompletableFuture<ThreadObject> create(ThreadRequest request) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, THREAD_URL, HttpMethod.POST, request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, ThreadObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<ThreadObject> retrieve(String threadId) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, THREAD_URL + "/" + threadId, HttpMethod.GET, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, ThreadObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<ThreadObject> modify(String threadId, ThreadRequest request) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, THREAD_URL + "/" + threadId, HttpMethod.POST, request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, ThreadObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<ThreadDeleteResponse> delete(String threadId) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, THREAD_URL + "/" + threadId, HttpMethod.DELETE, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, ThreadDeleteResponse.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    
}
