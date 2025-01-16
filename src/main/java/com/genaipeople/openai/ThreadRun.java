package com.genaipeople.openai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.assistant.thread.run.RunObject;
import com.genaipeople.openai.assistant.thread.run.RunRequest;
import com.genaipeople.openai.assistant.thread.run.RunCreateRequest;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;

public class ThreadRun {
    private final String apiKey;
    private final String threadId;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, String> headers = new HashMap<>();
    private final String threadUrl = "https://api.openai.com/v1/threads";

    public ThreadRun(String apiKey, String threadId) {
        this.apiKey = apiKey;
        this.threadId = threadId;
        headers.put("OpenAI-Beta", "assistants=v2");
    }

    public CompletableFuture<RunObject> create(RunCreateRequest request) {
        String url = String.format("%s/%s/runs", threadUrl, threadId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.POST, request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, RunObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<RunObject> run(RunRequest request) {
        String url = String.format("%s/runs", threadUrl, threadId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.POST, request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, RunObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<RunObject> retrieve(String runId) {
        String url = String.format("%s/%s/runs/%s", threadUrl, threadId, runId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.GET, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, RunObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<RunObject> modify(String runId, Map<String, String> metadata) {
        String url = String.format("%s/%s/runs/%s", threadUrl, threadId, runId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.POST, Map.of("metadata", metadata), headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, RunObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<RunObject> cancel(String runId) {
        String url = String.format("%s/%s/runs/%s/cancel", threadUrl, threadId, runId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.POST, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, RunObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<RunObject> submitToolOutputs(String runId, List<Map<String, String>> toolOutputs) {
        String url = String.format("%s/%s/runs/%s/submit_tool_outputs", threadUrl, threadId, runId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.POST, Map.of("tool_outputs", toolOutputs), 
                headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, RunObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }
}
