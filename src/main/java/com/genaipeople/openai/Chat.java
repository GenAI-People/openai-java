package com.genaipeople.openai;

import java.util.concurrent.CompletableFuture;

import com.genaipeople.openai.text.ChatRequest;
import com.genaipeople.openai.text.ChatResponse;

public class Chat {
    public CompletableFuture<ChatResponse> complete(ChatRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            // Implement the actual chat completion logic here
            // For now, we'll return null as a placeholder
            return null;
        });
    }
}
