package com.genaipeople.openai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.assistant.thread.run.RunStepList;
import com.genaipeople.openai.assistant.thread.run.RunStepObject;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;

public class ThreadRunStep {
    private final String apiKey;
    private final String threadId;
    private final String runId;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, String> headers = new HashMap<>();
    private final String threadUrl = "https://api.openai.com/v1/threads";

    public ThreadRunStep(String apiKey, String threadId, String runId) {
        this.apiKey = apiKey;
        this.threadId = threadId;
        this.runId = runId;
        headers.put("OpenAI-Beta", "assistants=v2");
    }
    

    public CompletableFuture<List<RunStepObject>> list() {
        String url = String.format("%s/%s/runs/%s/steps", threadUrl, threadId, runId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.GET, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                RunStepList runStepList = OpenAICommons.stringToType(responseString, 
                    RunStepList.class, mapper);
                return CompletableFuture.completedFuture(runStepList.getData());
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<RunStepObject> retrieve(String stepId) {
        String url = String.format("%s/%s/runs/%s/steps/%s", threadUrl, threadId, runId, stepId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.GET, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, RunStepObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<List<RunStepObject>> list(int limit, String order, String after, String before) {
        Map<String, String> queryParams = new HashMap<>();
        if (limit > 0) queryParams.put("limit", String.valueOf(limit));
        if (order != null) queryParams.put("order", order);
        if (after != null) queryParams.put("after", after);
        if (before != null) queryParams.put("before", before);

        String url = String.format("%s/%s/runs/%s/steps", threadUrl, threadId, runId);
        if (!queryParams.isEmpty()) {
            url += "?" + queryParams.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .reduce((a, b) -> a + "&" + b)
                .get();
        }
        final String listUrl = url;
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, listUrl, HttpMethod.GET, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                RunStepList runStepList = OpenAICommons.stringToType(responseString, RunStepList.class, mapper);
                return CompletableFuture.completedFuture(runStepList.getData());
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }
}
