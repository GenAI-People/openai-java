package com.genaipeople.openai.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.net.http.HttpResponse.BodyHandlers;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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

    public static Flow.Publisher<String> makeStreamingRequest(String apiKey, String url, 
            HttpMethod method, Object requestBody) {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "text/event-stream")
                    .header("Authorization", "Bearer " + apiKey);

            if (method == HttpMethod.POST && requestBody != null) {
                String jsonBody = objectMapper.writeValueAsString(requestBody);
                requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonBody));
            }

            HttpRequest request = requestBuilder.build();

            httpClient.sendAsync(request, BodyHandlers.ofInputStream())
                    .thenAccept(response -> {
                        try (BufferedReader reader = new BufferedReader(
                                new InputStreamReader(response.body()))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                if (line.startsWith("data: ")) {
                                    String data = line.substring(6).trim();
                                    if (!data.equals("[DONE]")) {
                                        publisher.submit(data);
                                    } else {
                                        publisher.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            publisher.closeExceptionally(e);
                            return;
                        }
                    })
                    .exceptionally(e -> {
                        publisher.closeExceptionally(e);
                        return null;
                    });

        } catch (Exception e) {
            publisher.closeExceptionally(e);
        }

        return publisher;
    }
}
