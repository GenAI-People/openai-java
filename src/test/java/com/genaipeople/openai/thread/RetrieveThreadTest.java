package com.genaipeople.openai.thread;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.ExecutionThread;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.assistant.thread.ThreadObject;

public class RetrieveThreadTest {

    @Test
    public void testRetrieveThread() throws Exception {
        ExecutionThread thread = new ExecutionThread(OpenAI.API_KEY);
        ThreadObject result = thread.retrieve("thread_abc123").get();
        
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("thread", result.getObject());
        assertNotNull(result.getCreatedAt());
    }
} 