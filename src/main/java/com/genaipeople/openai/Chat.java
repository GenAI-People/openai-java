package com.genaipeople.openai;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
    
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;
import com.genaipeople.openai.text.ChatRequest;
import com.genaipeople.openai.text.ChatResponse;

public class Chat {
    private final String apiKey;
    private final String COMPLETION_URL = "https://api.openai.com/v1/chat/completions";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Chat(String apiKey){
        this.apiKey = apiKey;
    }

    public CompletableFuture<ChatResponse> complete(ChatRequest request) {
        return CompletableFuture.supplyAsync(() -> 
            RestClient.makeAsyncRequest(apiKey, COMPLETION_URL, HttpMethod.POST, request, null, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(OpenAICommons.stringToType(responseString, ChatResponse.class, objectMapper));
            } catch (InterruptedException e) {
                return CompletableFuture.failedFuture(new RuntimeException("Request interrupted", e));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(new RuntimeException(e.getMessage()));
            }
        });
    }

    public Flow.Publisher<ChatResponse> stream(ChatRequest request) {
        request.setStream(true);
        SubmissionPublisher<ChatResponse> publisher = new SubmissionPublisher<>();

        RestClient.makeStreamingRequest(apiKey, COMPLETION_URL, HttpMethod.POST, request, null, null)
            .subscribe(new Flow.Subscriber<String>() {
                @Override
                public void onSubscribe(Flow.Subscription subscription) {
                    subscription.request(Long.MAX_VALUE);
                }

                @Override
                public void onNext(String item) {
                    try {
                        publisher.submit(OpenAICommons.stringToType(item, ChatResponse.class, objectMapper));
                    } catch (Exception e) {
                        publisher.closeExceptionally(e);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    publisher.closeExceptionally(throwable);
                }

                @Override
                public void onComplete() {
                    publisher.close();
                }
            });

        return publisher;
    }
}