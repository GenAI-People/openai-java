package com.genaipeople.openai.thread;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.ExecutionThread;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.assistant.thread.ThreadObject;
import com.genaipeople.openai.assistant.thread.ThreadRequest;
import java.util.HashMap;
import java.util.Map;

public class ModifyThreadTest {

    @Test
    public void testModifyThread() throws Exception {
        ExecutionThread thread = new ExecutionThread(OpenAI.API_KEY);
        Map<String, String> metadata = new HashMap<>();
        metadata.put("modified", "true");
        metadata.put("user", "abc123");
        
        ThreadRequest request = new ThreadRequest();
        request.setMetadata(metadata);
        
        ThreadObject result = thread.modify("thread_abc123", request).get();
        
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getMetadata());
        assertEquals("true", result.getMetadata().get("modified"));
        assertEquals("abc123", result.getMetadata().get("user"));
    }
} 