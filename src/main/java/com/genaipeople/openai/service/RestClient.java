package com.genaipeople.openai.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public enum HttpMethod {
        GET, POST, PUT, DELETE
    }

    public static CompletableFuture<String> makeAsyncRequest(String apiKey, String url, 
        HttpMethod method, Object requestBody) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey);

            switch (method) {
                case GET:
                    requestBuilder.GET();
                    break;
                case POST:
                    String jsonBody = objectMapper.writeValueAsString(requestBody);
                    requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonBody));
                    break;
                case PUT:
                    jsonBody = objectMapper.writeValueAsString(requestBody);
                    requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(jsonBody));
                    break;
                case DELETE:
                    requestBuilder.DELETE();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            }

            HttpRequest request = requestBuilder.build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body);
        } catch (IllegalArgumentException | java.io.IOException e) {
            CompletableFuture<String> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(e);
            return failedFuture;
        }
    }
}
