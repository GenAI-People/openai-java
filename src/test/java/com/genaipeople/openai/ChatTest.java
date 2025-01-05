package com.genaipeople.openai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.message.Message;
import com.genaipeople.openai.message.content.TextContent;
import com.genaipeople.openai.response.Choice;
import com.genaipeople.openai.response.ContentToken;
import com.genaipeople.openai.response.Delta;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;
import com.genaipeople.openai.text.ChatRequest;
import com.genaipeople.openai.text.ChatResponse;

class ChatTest {

    private Chat chat;
    private static final String COMPLETION_URL = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL = "gpt-4o";

    @BeforeEach
    void setUp() {
        chat = new Chat(OpenAI.API_KEY);
    }

    @Test
    void testComplete() throws ExecutionException, InterruptedException {
        List<Message> messages = Arrays.asList(new Message("Hello", Role.user));
        ChatRequest request = new ChatRequest(messages, MODEL);
        ChatResponse expectedResponse = new ChatResponse();
        String jsonResponse = "{\"id\":\"test-id\",\"object\":\"chat.completion\"}";

        try (MockedStatic<RestClient> mockedRestClient = mockStatic(RestClient.class)) {
            mockedRestClient.when(() -> RestClient.makeAsyncRequest(OpenAI.API_KEY, COMPLETION_URL, HttpMethod.POST, request, null, null))
                    .thenReturn(CompletableFuture.completedFuture(jsonResponse));

            CompletableFuture<ChatResponse> futureResponse = chat.complete(request);
            ChatResponse actualResponse = futureResponse.get();

            assertNotNull(actualResponse);
            assertEquals(expectedResponse.getClass(), actualResponse.getClass());
        }
    }

    @Test
    void testCompleteWithLogprobs() throws ExecutionException, InterruptedException {
        List<Message> messages = Arrays.asList(new Message("Hello", Role.user));
        ChatRequest request = new ChatRequest(messages, MODEL);
        request.setLogprobs(true);
        ChatResponse expectedResponse = new ChatResponse();

        CompletableFuture<ChatResponse> futureResponse = chat.complete(request);
        ChatResponse actualResponse = futureResponse.get();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getClass(), actualResponse.getClass());

        Choice choice = actualResponse.getChoices().get(0);
        assertNotNull(choice.getLogprobs());
        assertTrue(choice.getLogprobs().getContent().size() > 0);
        ContentToken contentToken = choice.getLogprobs().getContent().get(0);
        assertNotNull(contentToken.getToken());
        assertTrue(contentToken.getToken().length() > 0);
    }

    @Test
    void testCompleteWithInterruptedException() throws ExecutionException, InterruptedException {
        ChatRequest request = new ChatRequest(Arrays.asList(new Message("Hello", Role.user)), MODEL);

        try (MockedStatic<RestClient> mockedRestClient = mockStatic(RestClient.class)) {
            CompletableFuture<String> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(new InterruptedException("Test interruption"));

            mockedRestClient.when(() -> RestClient.makeAsyncRequest(OpenAI.API_KEY, COMPLETION_URL, HttpMethod.POST, request, null, null))
                    .thenReturn(failedFuture);

            CompletableFuture<ChatResponse> futureResponse = chat.complete(request);
            ChatResponse actualResponse = futureResponse.get();

            assertNotNull(actualResponse);
            // Add more assertions based on how you want to handle this error case
        }
    }

    @Test
    void testCompleteWithExecutionException() throws ExecutionException, InterruptedException {
        ChatRequest request = new ChatRequest(Arrays.asList(new Message("Hello", Role.user)), MODEL);

        try (MockedStatic<RestClient> mockedRestClient = mockStatic(RestClient.class)) {
            CompletableFuture<String> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(new ExecutionException(new RuntimeException("Test execution exception")));

            mockedRestClient.when(() -> RestClient.makeAsyncRequest(OpenAI.API_KEY, COMPLETION_URL, HttpMethod.POST, request, null, null))
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

        ObjectMapper mapper = new ObjectMapper();
        ChatResponse actualResponse;
        try {
            actualResponse = OpenAICommons.stringToType(jsonResponse, ChatResponse.class, mapper);
            System.out.println(actualResponse);
            assertNotNull(actualResponse);
            assertEquals(expectedResponse.getId(), actualResponse.getId());
            assertEquals(expectedResponse.getObject(), actualResponse.getObject()); 
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to convert JSON to ChatResponse");
        }
        
    }

    @Test
    void testStringToChatResponseWithInvalidJson() {
        String invalidJson = "invalid json";
        ObjectMapper mapper = new ObjectMapper();
        assertThrows(RuntimeException.class, () -> OpenAICommons.stringToType(invalidJson,  ChatResponse.class, mapper));
    }

    @Test
    void testChatCompletionResponse() throws JsonProcessingException, ExecutionException, InterruptedException {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("You are a helpful assistant.", Role.system));
        messages.add(new Message("Hello!", Role.user));
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
            mockedRestClient.when(() -> RestClient.makeAsyncRequest(OpenAI.API_KEY, COMPLETION_URL, HttpMethod.POST, chatRequest, null, null))
                    .thenReturn(CompletableFuture.completedFuture(jsonResponse));

            CompletableFuture<ChatResponse> futureResponse = chat.complete(chatRequest);
            ChatResponse actualResponse = futureResponse.get();
            ObjectMapper mapper = new ObjectMapper();
            ChatResponse expectedResponse;
            try {
                expectedResponse = OpenAICommons.stringToType(jsonResponse, ChatResponse.class, mapper);
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
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to convert JSON to ChatResponse");
            }
        }
    }

    @Test
    void testImageChatCompletionResponse() throws JsonProcessingException, ExecutionException, InterruptedException {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("You are a helpful assistant.", Role.system));
        messages.add(new Message("What's in this image?", 
                "https://miro.medium.com/v2/resize:fit:828/format:webp/1*LLxq7oaQj8dmPW3wKZTMJA.jpeg", 
                Role.user));
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
            mockedRestClient.when(() -> RestClient.makeAsyncRequest(OpenAI.API_KEY, COMPLETION_URL, HttpMethod.POST, chatRequest, null, null))
                    .thenReturn(CompletableFuture.completedFuture(jsonResponse));

            CompletableFuture<ChatResponse> futureResponse = chat.complete(chatRequest);
            ChatResponse actualResponse = futureResponse.get();

            ObjectMapper mapper = new ObjectMapper();
            ChatResponse expectedResponse;
            try {
                expectedResponse = OpenAICommons.stringToType(jsonResponse, ChatResponse.class, mapper);
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
            } catch (Exception e) {
                e.printStackTrace();
                fail("Failed to convert JSON to ChatResponse");
            }
        }
    }

    @Test
    void testImageChatCompletionResponseWithInvalidImageUrl() throws JsonProcessingException, ExecutionException, InterruptedException {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("You are a helpful assistant.", Role.system));
        messages.add(new Message("What's in this image?", null, 
                Role.user));
        ChatRequest chatRequest = new ChatRequest(messages, MODEL);

        String jsonResponse = "{\n" +
                "  \"id\": \"chatcmpl-789\",\n" +
                "  \"object\": \"chat.completion\",\n" +
                "  \"created\": 1677652295,\n" +
                "  \"model\": \"gpt-4-vision-preview\",\n" +
                "  \"system_fingerprint\": \"fp_44709d6fcb\",\n" +
                "  \"choices\": [{\n" +
                "    \"index\": 0,\n" +
                "    \"message\": {\n" +
                "      \"role\": \"assistant\",\n" +
                "      \"content\": \"The image URL provided is invalid.\"\n" +
                "    },\n" +
                "    \"logprobs\": null,\n" +
                "    \"finish_reason\": \"stop\"\n" +
                "  }],\n" +
                "  \"usage\": {\n" +
                "    \"prompt_tokens\": 60,\n" +
                "    \"completion_tokens\": 90,\n" +
                "    \"total_tokens\": 150\n" +
                "  }\n" +
                "}";

        try (MockedStatic<RestClient> mockedRestClient = mockStatic(RestClient.class)) {
            mockedRestClient.when(() -> RestClient.makeAsyncRequest(OpenAI.API_KEY, COMPLETION_URL, HttpMethod.POST, chatRequest, null, null))
                    .thenReturn(CompletableFuture.completedFuture(jsonResponse));

            CompletableFuture<ChatResponse> futureResponse = chat.complete(chatRequest);
            try {
                futureResponse.get();
                fail("Expected an exception, but got a response");
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getCause().getMessage());
                assertTrue(e.getCause().getMessage().contains("invalid_type"), 
                "Error message should contain 'invalid_type'");
            }
        }
    }

    @Test
    void testImageChatCompletionResponseWithInvalidImageUrlFormat() throws InterruptedException {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("You are a helpful assistant.", Role.system));
        messages.add(new Message("What's in this image?", "1*LLxq7oaQj8dmPW3wKZTMJA.jpeg", Role.user));
        ChatRequest chatRequest = new ChatRequest(messages, MODEL);

        String jsonResponse = "Response: {\n" +
                "  \"error\": {\n" +
                "    \"message\": \"Invalid image.\",\n" +
                "    \"type\": \"invalid_request_error\",\n" +
                "    \"param\": null,\n" +
                "    \"code\": \"invalid_image\"\n" +
                "  }\n" +
                "}";

        try (MockedStatic<RestClient> mockedRestClient = mockStatic(RestClient.class)) {
            mockedRestClient.when(() -> RestClient.makeAsyncRequest(OpenAI.API_KEY, COMPLETION_URL, HttpMethod.POST, chatRequest, null, null))
                    .thenReturn(CompletableFuture.completedFuture(jsonResponse));
            
            CompletableFuture<ChatResponse> futureResponse = chat.complete(chatRequest);
            futureResponse.handle((response, throwable) -> {
                if (throwable != null) {
                    System.out.println(throwable.getMessage());
                    assertTrue(throwable.getMessage().contains("invalid_image_url"), 
                    "Error message should contain 'invalid_image_url'");
                }
                return response;
            });
            try {
                futureResponse.get();
                fail("Expected an exception, but got a response");
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getCause().getMessage());
                assertTrue(e.getCause().getMessage().contains("invalid_image_url"), 
                "Error message should contain 'invalid_type'");
                // Handle or rethrow the exception as needed
            }
        }
    }

    @Test
    void testErrorResponse() throws JsonProcessingException {
        String errorResponse = "{\n" +
                "  \"error\": {\n" +
                "    \"message\": \"Invalid image.\",\n" +
                "    \"type\": \"invalid_request_error\",\n" +
                "    \"param\": null,\n" +
                "    \"code\": \"invalid_image\"\n" +
                "  }\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> OpenAICommons.stringToType(errorResponse, ChatResponse.class, mapper));
        assertEquals("Code: invalid_image Message: Invalid image.", exception.getMessage());
    }

    @Test
    void testStream() {
        ChatRequest request = new ChatRequest(Arrays.asList(new Message("Hello", Role.user)), MODEL);
        Flow.Publisher<ChatResponse> stream = chat.stream(request);
        Object lock = new Object();
        stream.subscribe(new Flow.Subscriber<ChatResponse>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
                System.out.println("Subscription requested");
            }

            @Override
            public void onNext(ChatResponse item) {
                Choice choice = item.getChoices().get(0);   
                Delta delta = choice.getDelta();
                if (choice.getFinishReason() == null) {
                    System.out.println("Item: " + delta.getContent().getContent());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onComplete() {
                System.out.println("Stream completed in Test");
                synchronized (lock) {
                    lock.notify();
                }
            }
        });
        assertNotNull(stream);
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } 

    @Test
    void testStreamNoInput() {
        ChatRequest request = new ChatRequest(Arrays.asList(new Message("", Role.user)), MODEL);
        Flow.Publisher<ChatResponse> stream = chat.stream(request);
        Object lock = new Object();
        stream.subscribe(new Flow.Subscriber<ChatResponse>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
                System.out.println("Subscription requested");
            }

            @Override
            public void onNext(ChatResponse item) {
                Choice choice = item.getChoices().get(0);   
                Delta delta = choice.getDelta();
                if (choice.getFinishReason() == null) {
                    System.out.println("Item: " + delta.getContent().getContent());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                fail("Stream failed in Test");
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onComplete() {
                System.out.println("Stream completed in Test");
                synchronized (lock) {
                    lock.notify();
                }
            }
        });
        assertNotNull(stream);
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } 

    @Test
    void testStreamingImageChatCompletionResponse() throws JsonProcessingException, ExecutionException, InterruptedException {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("You are a helpful assistant.", Role.system));
        messages.add(new Message("What's in this image?", 
                "https://miro.medium.com/v2/resize:fit:828/format:webp/1*LLxq7oaQj8dmPW3wKZTMJA.jpeg", 
                Role.user));
        ChatRequest request = new ChatRequest(messages, MODEL);

        Flow.Publisher<ChatResponse> stream = chat.stream(request);
        Object lock = new Object();
        stream.subscribe(new Flow.Subscriber<ChatResponse>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
                System.out.println("Subscription requested");
            }

            @Override
            public void onNext(ChatResponse item) {
                Choice choice = item.getChoices().get(0);   
                Delta delta = choice.getDelta();
                if (choice.getFinishReason() == null) {
                    System.out.println("Item: " + delta.getContent().getContent());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                fail("Stream failed in Test");
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onComplete() {
                System.out.println("Stream completed in Test");
                synchronized (lock) {
                    lock.notify();
                }
            }
        });
        assertNotNull(stream);
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
