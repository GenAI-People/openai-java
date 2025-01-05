package com.genaipeople.openai;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;
import com.genaipeople.openai.vector.VectorCreateRequest;
import com.genaipeople.openai.vector.VectorStoreDeleteResponse;
import com.genaipeople.openai.vector.VectorStoreListResponse;
import com.genaipeople.openai.vector.VectorStoreObject;
import com.genaipeople.openai.vector.VectorUpdateRequest;

public class VectorStore {
    private final String apiKey;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, String> headers = new HashMap<>();
    private final String vectorUrl = "https://api.openai.com/v1/vector_stores";

    public VectorStore(String apiKey) {
        this.apiKey = apiKey;
        headers.put("OpenAI-Beta", "assistants=v2");
    }

    public CompletableFuture<VectorStoreListResponse<VectorStoreObject>> list(Integer limit, String order, String after, String before) {
        String url = String.format("%s", vectorUrl);
        Map<String, String> queryParams = new HashMap<>();
        if (limit != null) {
            queryParams.put("limit", String.valueOf(limit));
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
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.GET, null, headers, queryParams)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(mapper.readValue(responseString,
                    new TypeReference<VectorStoreListResponse<VectorStoreObject>>() {}));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<VectorStoreObject> retrieve(String vectorStoreId) {
        String url = String.format("%s/%s", vectorUrl, vectorStoreId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.GET, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, VectorStoreObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<VectorStoreDeleteResponse> delete(String vectorStoreId) {
        String url = String.format("%s/%s", vectorUrl, vectorStoreId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.DELETE, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, VectorStoreDeleteResponse.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<VectorStoreObject> update(String vectorStoreId, VectorUpdateRequest request) {
        String url = String.format("%s/%s", vectorUrl, vectorStoreId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.POST, request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, VectorStoreObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<VectorStoreObject> create(VectorCreateRequest request) {
        String url = String.format("%s", vectorUrl);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.POST, request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, VectorStoreObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }
}
