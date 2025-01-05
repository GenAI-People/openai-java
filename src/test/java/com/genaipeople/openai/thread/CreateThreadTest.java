package com.genaipeople.openai.thread;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.genaipeople.openai.ExecutionThread;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.assistant.thread.ThreadObject;
import com.genaipeople.openai.assistant.thread.ThreadRequest;
import java.util.HashMap;
import java.util.Map;

public class CreateThreadTest {

    @Test
    public void testCreateEmptyThread() throws Exception {
        ExecutionThread thread = new ExecutionThread(OpenAI.API_KEY);
        ThreadObject result = thread.create().get();
        
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("thread", result.getObject());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    public void testCreateThreadWithMetadata() throws Exception {
        ExecutionThread thread = new ExecutionThread(OpenAI.API_KEY);
        Map<String, String> metadata = new HashMap<>();
        metadata.put("user_id", "user123");
        metadata.put("session_id", "session456");
        
        ThreadRequest request = new ThreadRequest();
        request.setMetadata(metadata);
        
        ThreadObject result = thread.create(request).get();
        
        assertNotNull(result);
        assertNotNull(result.getMetadata());
        assertEquals("user123", result.getMetadata().get("user_id"));
        assertEquals("session456", result.getMetadata().get("session_id"));
    }
} 