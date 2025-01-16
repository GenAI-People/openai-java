package com.genaipeople.openai.assistant.thread.run;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.genaipeople.openai.Assistant;
import com.genaipeople.openai.ExecutionThread;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.ThreadRun;
import com.genaipeople.openai.assistant.AssistantRequest;
import com.genaipeople.openai.assistant.response.AssistantObject;
import com.genaipeople.openai.assistant.thread.ThreadObject;
import com.genaipeople.openai.assistant.thread.ThreadRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateRunTest {
    private Assistant assistant;
    private AssistantObject assistantObject;
    private ExecutionThread executionThread;
    private ThreadObject threadObject;
    @BeforeEach
    void setUp() {
        try {
            assistant = new Assistant(OpenAI.API_KEY);
            AssistantRequest assistantRequest = createAssistantRequest();
            assistantRequest.setModel("gpt-4o");
            assistantObject = assistant.create(assistantRequest).get();
            executionThread = new ExecutionThread(OpenAI.API_KEY);
            ThreadRequest threadRequest = new ThreadRequest();
            threadObject = executionThread.create(threadRequest).get();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to create thread run");
        }
    }

    @AfterEach
    void tearDown() {
        executionThread.delete(threadObject.getId());
        assistant.delete(assistantObject.getId());
    }

    private AssistantRequest createAssistantRequest() {
        AssistantRequest assistantRequest = new AssistantRequest();
        assistantRequest.setName("Test Assistant");
        assistantRequest.setInstructions("This is a test assistant");
        return assistantRequest;
    }

    @Test
    public void testCreateRun() throws Exception {
        ThreadRun threadRun = new ThreadRun(OpenAI.API_KEY, threadObject.getId());

        RunCreateRequest request = new RunCreateRequest(assistantObject.getId());
        request.setModel("gpt-4o");
        
        RunObject result = threadRun.create(request).get();
        
        assertNotNull(result);
        assertEquals("thread.run", result.getObject());
        assertEquals(assistantObject.getId(), result.getAssistantId());
        assertEquals("gpt-4o", result.getModel());
    }

    @Test
    public void testCreateRunWithMetadata() throws Exception {
        ThreadRun threadRun = new ThreadRun(OpenAI.API_KEY, threadObject.getId());
        RunCreateRequest request = new RunCreateRequest(assistantObject.getId());
        
        Map<String, String> metadata = new HashMap<>();
        metadata.put("user_id", "user123");
        metadata.put("session_id", "session456");
        request.setMetadata(metadata);
        
        RunObject result = threadRun.create(request).get();
        
        assertNotNull(result);
        assertNotNull(result.getMetadata());
        assertEquals("user123", result.getMetadata().get("user_id"));
        assertEquals("session456", result.getMetadata().get("session_id"));
    }
} 