package com.genaipeople.openai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.assistant.message.ThreadMessageObject;
import com.genaipeople.openai.assistant.message.ThreadMessageRequest;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;
import com.genaipeople.openai.assistant.message.ThreadMessageDeleteResponse;    

public class AssistantThreadMessage {
    private final String apiKey;
    private final String THREAD_URL = "https://api.openai.com/v1/threads";
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, String> headers = new HashMap<>();

    public AssistantThreadMessage(String apiKey) {
        this.apiKey = apiKey;
        headers.put("OpenAI-Beta", "assistants=v2");
    }

    public CompletableFuture<ThreadMessageObject> create(String threadId, ThreadMessageRequest request) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, THREAD_URL + "/" + threadId + "/messages", HttpMethod.POST, 
                request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, 
                ThreadMessageObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    /**
     * Lists messages for a given thread.
     *
     * @param threadId The ID of the thread to list messages for
     * @param limit Optional limit on number of objects (1-100, default 20)
     * @param order Optional sort order ("asc" or "desc", default "desc")
     * @param after Optional cursor for pagination (after this object ID)
     * @param before Optional cursor for pagination (before this object ID)
     * @param runId Optional filter for messages from a specific run
     * @return CompletableFuture with list of thread messages
     */
    public CompletableFuture<List<ThreadMessageObject>> list(String threadId, Integer limit, String order,
            String after, String before, String runId) {
        
        StringBuilder urlBuilder = new StringBuilder(THREAD_URL)
            .append("/").append(threadId).append("/messages");
        
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
        if (runId != null) {
            queryParams.put("run_id", runId);
        }

        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, urlBuilder.toString(), HttpMethod.GET, 
                null, headers, queryParams)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, 
                new TypeReference<List<ThreadMessageObject>>() {}, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    /**
     * Lists messages for a given thread with default parameters.
     *
     * @param threadId The ID of the thread to list messages for
     * @return CompletableFuture with list of thread messages
     */
    public CompletableFuture<List<ThreadMessageObject>> list(String threadId) {
        return list(threadId, null, null, null, null, null);
    }

    public CompletableFuture<ThreadMessageObject> retrieve(String threadId, String messageId) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, THREAD_URL + "/" + threadId + "/messages/" + messageId, HttpMethod.GET, 
                null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, 
                    ThreadMessageObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<ThreadMessageDeleteResponse> delete(String threadId, String messageId) {
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, THREAD_URL + "/" + threadId + "/messages/" + messageId, 
                HttpMethod.DELETE, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, 
                    ThreadMessageDeleteResponse.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    /**
     * Modifies a message's metadata.
     *
     * @param threadId The ID of the thread to which the message belongs
     * @param messageId The ID of the message to modify
     * @param metadata Key-value pairs to update the message's metadata
     * @return CompletableFuture with the modified message object
     */
    public CompletableFuture<ThreadMessageObject> modify(String threadId, String messageId, Map<String, String> metadata) {
        Map<String, Object> request = new HashMap<>();
        request.put("metadata", metadata);

        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, THREAD_URL + "/" + threadId + "/messages/" + messageId, 
                HttpMethod.POST, request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, 
                    ThreadMessageObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }
}
