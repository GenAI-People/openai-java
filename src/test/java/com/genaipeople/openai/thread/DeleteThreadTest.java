package com.genaipeople.openai.thread;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.assistant.thread.ThreadDeleteResponse;
import com.genaipeople.openai.ExecutionThread;
public class DeleteThreadTest {

    @Test
    public void testDeleteThread() throws Exception {
        ExecutionThread thread = new ExecutionThread(OpenAI.API_KEY);
        ThreadDeleteResponse result = thread.delete("thread_abc123").get();
        
        assertNotNull(result);
        assertTrue(result.getDeleted());
        assertEquals("thread_abc123", result.getId());
    }
} 