package com.genaipeople.openai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genaipeople.openai.message.Message;
import com.genaipeople.openai.message.content.TextContent;
import com.genaipeople.openai.response.Choice;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;
import com.genaipeople.openai.text.ChatRequest;
import com.genaipeople.openai.text.ChatResponse;

class ChatTest {

    private Chat chat;
    private static final String API_KEY = "";
    private static final String COMPLETION_URL = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL = "gpt-4o";

    @BeforeEach
    void setUp() {
        chat = new Chat(API_KEY);
    }

    @Test
    void testComplete() throws ExecutionException, InterruptedException {
        List<Message> messages = Arrays.asList(new Message("Hello", Role.USER));
        ChatRequest request = new ChatRequest(messages, MODEL);
        ChatResponse expectedResponse = new ChatResponse();
        String jsonResponse = "{\"id\":\"test-id\",\"object\":\"chat.completion\"}";

        try (MockedStatic<RestClient> mockedRestClient = mockStatic(RestClient.class)) {
            mockedRestClient.when(() -> RestClient.makeAsyncRequest(API_KEY, COMPLETION_URL, HttpMethod.POST, request))
                    .thenReturn(CompletableFuture.completedFuture(jsonResponse));

            CompletableFuture<ChatResponse> futureResponse = chat.complete(request);
            ChatResponse actualResponse = futureResponse.get();

            assertNotNull(actualResponse);
            assertEquals(expectedResponse.getClass(), actualResponse.getClass());
        }
    }

    @Test
    void testCompleteWithInterruptedException() throws ExecutionException, InterruptedException {
        ChatRequest request = new ChatRequest(Arrays.asList(new Message("Hello", Role.USER)), MODEL);

        try (MockedStatic<RestClient> mockedRestClient = mockStatic(RestClient.class)) {
            CompletableFuture<String> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(new InterruptedException("Test interruption"));

            mockedRestClient.when(() -> RestClient.makeAsyncRequest(API_KEY, COMPLETION_URL, HttpMethod.POST, request))
                    .thenReturn(failedFuture);

            CompletableFuture<ChatResponse> futureResponse = chat.complete(request);
            ChatResponse actualResponse = futureResponse.get();

            assertNotNull(actualResponse);
            // Add more assertions based on how you want to handle this error case
        }
    }

    @Test
    void testCompleteWithExecutionException() throws ExecutionException, InterruptedException {
        ChatRequest request = new ChatRequest(Arrays.asList(new Message("Hello", Role.USER)), MODEL);

        try (MockedStatic<RestClient> mockedRestClient = mockStatic(RestClient.class)) {
            CompletableFuture<String> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(new ExecutionException(new RuntimeException("Test execution exception")));

            mockedRestClient.when(() -> RestClient.makeAsyncRequest(API_KEY, COMPLETION_URL, HttpMethod.POST, request))
                    .thenReturn(failedFuture);

            CompletableFuture<ChatResponse> futureResponse = chat.complete(request);
            ChatResponse actualResponse = futureResponse.get();

            assertNotNull(actualResponse);
            // Add more assertions based on how you want to handle this error case
        }
    }

    @Test
    void testStringToChatResponse() throws JsonProcessingException {
        String jsonResponse = "{\n" + //
                        "  \"id\": \"chatcmpl-A8JuM6pdfIt5E9V7kGIE4rbBRqz4L\",\n" + //
                        "  \"object\": \"chat.completion\",\n" + //
                        "  \"created\": 1726546514,\n" + //
                        "  \"model\": \"gpt-3.5-turbo-0125\",\n" + //
                        "  \"choices\": [\n" + //
                        "    {\n" + //
                        "      \"index\": 0,\n" + //
                        "      \"message\": {\n" + //
                        "        \"role\": \"assistant\",\n" + //
                        "        \"content\": \"Hello! How can I assist you today?\",\n" + //
                        "        \"refusal\": null\n" + //
                        "      },\n" + //
                        "      \"logprobs\": null,\n" + //
                        "      \"finish_reason\": \"stop\"\n" + //
                        "    }\n" + //
                        "  ],\n" + //
                        "  \"usage\": {\n" + //
                        "    \"prompt_tokens\": 8,\n" + //
                        "    \"completion_tokens\": 9,\n" + //
                        "    \"total_tokens\": 17,\n" + //
                        "    \"completion_tokens_details\": {\n" + //
                        "      \"reasoning_tokens\": 0\n" + //
                        "    }\n" + //
                        "  },\n" + //
                        "  \"system_fingerprint\": null\n" + //
                        "}";
        ChatResponse expectedResponse = new ChatResponse();
        expectedResponse.setId("chatcmpl-A8JuM6pdfIt5E9V7kGIE4rbBRqz4L");
        expectedResponse.setObject("chat.completion");

        ChatResponse actualResponse = chat.stringToChatResponse(jsonResponse);
        System.out.println(actualResponse);
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getObject(), actualResponse.getObject());
    }

    @Test
    void testStringToChatResponseWithInvalidJson() {
        String invalidJson = "invalid json";

        assertThrows(RuntimeException.class, () -> chat.stringToChatResponse(invalidJson));
    }

    @Test
    void testChatCompletionResponse() throws JsonProcessingException, ExecutionException, InterruptedException {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("You are a helpful assistant.", Role.SYSTEM));
        messages.add(new Message("Hello!", Role.USER));
        ChatRequest chatRequest = new ChatRequest(messages, MODEL);

        String jsonResponse = "{\n" +
                "  \"id\": \"chatcmpl-123\",\n" +
                "  \"object\": \"chat.completion\",\n" +
                "  \"created\": 1677652288,\n" +
                "  \"model\": \"gpt-4o-mini\",\n" +
                "  \"system_fingerprint\": \"fp_44709d6fcb\",\n" +
                "  \"choices\": [{\n" +
                "    \"index\": 0,\n" +
                "    \"message\": {\n" +
                "      \"role\": \"assistant\",\n" +
                "      \"content\": \"\\n\\nHello there, how may I assist you today?\"\n" +
                "    },\n" +
                "    \"logprobs\": null,\n" +
                "    \"finish_reason\": \"stop\"\n" +
                "  }],\n" +
                "  \"usage\": {\n" +
                "    \"prompt_tokens\": 9,\n" +
                "    \"completion_tokens\": 12,\n" +
                "    \"total_tokens\": 21\n" +
                "  }\n" +
                "}";

        try (MockedStatic<RestClient> mockedRestClient = mockStatic(RestClient.class)) {
            mockedRestClient.when(() -> RestClient.makeAsyncRequest(API_KEY, COMPLETION_URL, HttpMethod.POST, chatRequest))
                    .thenReturn(CompletableFuture.completedFuture(jsonResponse));

            CompletableFuture<ChatResponse> futureResponse = chat.complete(chatRequest);
            ChatResponse actualResponse = futureResponse.get();

            ChatResponse expectedResponse = chat.stringToChatResponse(jsonResponse);
            System.out.println(expectedResponse);
            assertNotNull(actualResponse);
            //assertEquals(expectedResponse.getId(), actualResponse.getId());
           
            List<Choice> actualChoices = actualResponse.getChoices();
            List<Choice> expectedChoices = expectedResponse.getChoices();
            assertNotNull(actualChoices);
            assertEquals(expectedChoices.size(), actualChoices.size());
            for (int i = 0; i < expectedChoices.size(); i++) {
                Choice expectedChoice = expectedChoices.get(i);
                Choice actualChoice = actualChoices.get(i);
                assertEquals(expectedChoice.getMessage().getRole(), actualChoice.getMessage().getRole());
                assertNotNull(actualChoice.getMessage().getContent());
                TextContent actualTextContent = (TextContent) actualChoice.getMessage().getContent();
                System.out.println(actualTextContent.getContent());
                assertTrue(actualTextContent.getContent().length() > 0);
            }
        }
    }

    @Test
    void testImageChatCompletionResponse() throws JsonProcessingException, ExecutionException, InterruptedException {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("You are a helpful assistant.", Role.SYSTEM));
        messages.add(new Message("What's in this image?", 
                "https://miro.medium.com/v2/resize:fit:828/format:webp/1*LLxq7oaQj8dmPW3wKZTMJA.jpeg", 
                Role.USER));
        ChatRequest chatRequest = new ChatRequest(messages, MODEL);

        String jsonResponse = "{\n" +
                "  \"id\": \"chatcmpl-123\",\n" +
                "  \"object\": \"chat.completion\",\n" +
                "  \"created\": 1677652288,\n" +
                "  \"model\": \"gpt-4-vision-preview\",\n" +
                "  \"system_fingerprint\": \"fp_44709d6fcb\",\n" +
                "  \"choices\": [{\n" +
                "    \"index\": 0,\n" +
                "    \"message\": {\n" +
                "      \"role\": \"assistant\",\n" +
                "      \"content\": \"The image shows a coffee cup.\"\n" +
                "    },\n" +
                "    \"logprobs\": null,\n" +
                "    \"finish_reason\": \"stop\"\n" +
                "  }],\n" +
                "  \"usage\": {\n" +
                "    \"prompt_tokens\": 50,\n" +
                "    \"completion_tokens\": 73,\n" +
                "    \"total_tokens\": 123\n" +
                "  }\n" +
                "}";

        try (MockedStatic<RestClient> mockedRestClient = mockStatic(RestClient.class)) {
            mockedRestClient.when(() -> RestClient.makeAsyncRequest(API_KEY, COMPLETION_URL, HttpMethod.POST, chatRequest))
                    .thenReturn(CompletableFuture.completedFuture(jsonResponse));

            CompletableFuture<ChatResponse> futureResponse = chat.complete(chatRequest);
            ChatResponse actualResponse = futureResponse.get();

            ChatResponse expectedResponse = chat.stringToChatResponse(jsonResponse);
            System.out.println(expectedResponse);
            assertNotNull(actualResponse);
           
            List<Choice> actualChoices = actualResponse.getChoices();
            List<Choice> expectedChoices = expectedResponse.getChoices();
            assertNotNull(actualChoices);
            assertEquals(expectedChoices.size(), actualChoices.size());
            for (int i = 0; i < expectedChoices.size(); i++) {
                Choice expectedChoice = expectedChoices.get(i);
                Choice actualChoice = actualChoices.get(i);
                assertEquals(expectedChoice.getMessage().getRole(), actualChoice.getMessage().getRole());
                assertNotNull(actualChoice.getMessage().getContent());
                TextContent actualTextContent = (TextContent) actualChoice.getMessage().getContent();
                System.out.println(actualTextContent.getContent());
                assertTrue(actualTextContent.getContent().length() > 0);
            }
        }
    }
}

