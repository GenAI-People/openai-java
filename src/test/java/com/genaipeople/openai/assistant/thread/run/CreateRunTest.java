package com.genaipeople.openai.assistant.thread.run;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.ThreadRun;

import java.util.HashMap;
import java.util.Map;

public class CreateRunTest {

    @Test
    public void testCreateRun() throws Exception {
        ThreadRun threadRun = new ThreadRun(OpenAI.API_KEY, "thread_abc123");
        RunRequest request = new RunRequest("asst_abc123");
        request.setModel("gpt-4o");
        
        RunObject result = threadRun.create(request).get();
        
        assertNotNull(result);
        assertEquals("thread.run", result.getObject());
        assertEquals("thread_abc123", result.getThreadId());
        assertEquals("asst_abc123", result.getAssistantId());
        assertEquals("gpt-4o", result.getModel());
    }

    @Test
    public void testCreateRunWithMetadata() throws Exception {
        ThreadRun threadRun = new ThreadRun(OpenAI.API_KEY, "thread_abc123");
        RunRequest request = new RunRequest("asst_abc123");
        
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