package com.genaipeople.openai.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;
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
                    if (requestBody != null) {
                        // Convert request body to query string and append to URL
                        @SuppressWarnings("unchecked")
                        Map<String, Object> params = objectMapper.convertValue(requestBody, Map.class);
                        String queryParams = params.entrySet().stream()
                            .map(e -> e.getValue() != null ? e.getKey() + "=" + e.getValue() : null)
                            .filter(Objects::nonNull)
                            .collect(Collectors.joining("&"));
                        requestBuilder.uri(URI.create(url + (url.contains("?") ? "&" : "?") + queryParams));
                    }
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

    public static CompletableFuture<String> makeAsyncFileRequest(String apiKey, String url, 
        HttpMethod method, Object requestBody) throws IOException {
        String boundary = "Boundary-" + System.currentTimeMillis();
        
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .header("Authorization", "Bearer " + apiKey);

        switch (method) {
            case POST:
                Map<String, Object> fields = objectMapper.convertValue(requestBody, 
                    new TypeReference<Map<String, Object>>() {});
                
                byte[] body = createMultipartBody(fields, boundary);
                requestBuilder.POST(HttpRequest.BodyPublishers.ofByteArray(body));
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        HttpRequest request = requestBuilder.build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

    private static byte[] createMultipartBody(Map<String, Object> fields, String boundary) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            builder.append("--").append(boundary).append("\r\n");
            if (entry.getValue() instanceof byte[]) {
                builder.append("Content-Disposition: form-data; name=\"")
                       .append(entry.getKey())
                       .append("\"; filename=\"file\"\r\n");
                builder.append("Content-Type: application/octet-stream\r\n\r\n");
                builder.append(new String((byte[]) entry.getValue()));
            } else {
                builder.append("Content-Disposition: form-data; name=\"")
                       .append(entry.getKey())
                       .append("\"\r\n\r\n");
                builder.append(entry.getValue());
            }
            builder.append("\r\n");
        }
        builder.append("--").append(boundary).append("--\r\n");
        return builder.toString().getBytes();
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
