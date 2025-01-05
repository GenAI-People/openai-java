package com.genaipeople.openai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;
import com.genaipeople.openai.vector.VectorStoreDeleteResponse;
import com.genaipeople.openai.vector.VectorStoreFileObject;
import com.genaipeople.openai.vector.VectorStoreFileRequest;
import com.genaipeople.openai.vector.VectorStoreListResponse;

public class VectorStoreFile {
    private final String apiKey;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, String> headers = new HashMap<>();
    private final String vectorUrl = "https://api.openai.com/v1/vector_stores";

    public VectorStoreFile(String apiKey) {
        this.apiKey = apiKey;
        headers.put("OpenAI-Beta", "assistants=v2");
    }

    public CompletableFuture<VectorStoreFileObject> create(String vectorStoreId, VectorStoreFileRequest request) {
        String url = String.format("%s/%s/files", vectorUrl, vectorStoreId);
        

        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.POST, request, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(
                    OpenAICommons.stringToType(responseString, VectorStoreFileObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<VectorStoreFileObject> retrieve(String vectorStoreId, String fileId) {
        String url = String.format("%s/%s/files/%s", vectorUrl, vectorStoreId, fileId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.GET, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(
                    OpenAICommons.stringToType(responseString, VectorStoreFileObject.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<VectorStoreDeleteResponse> delete(String vectorStoreId, String fileId) {
        String url = String.format("%s/%s/files/%s", vectorUrl, vectorStoreId, fileId);
        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.DELETE, null, headers, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(
                    OpenAICommons.stringToType(responseString, VectorStoreDeleteResponse.class, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<VectorStoreListResponse<VectorStoreFileObject>> list(Integer limit, String order, 
            String after, String before, String filter, String vectorStoreId) {
        String url = String.format("%s/%s/files", vectorUrl, vectorStoreId);
        Map<String, String> queryParams = new HashMap<>();
        if (limit != null) queryParams.put("limit", String.valueOf(limit));
        if (order != null) queryParams.put("order", order);
        if (after != null) queryParams.put("after", after);
        if (before != null) queryParams.put("before", before);
        if (filter != null) queryParams.put("filter", filter);

        return CompletableFuture.supplyAsync(() ->
            RestClient.makeAsyncRequest(apiKey, url, HttpMethod.GET, null, headers, queryParams)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(
                    OpenAICommons.stringToType(responseString, 
                       new TypeReference<VectorStoreListResponse<VectorStoreFileObject>>() {}, mapper));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }
}
