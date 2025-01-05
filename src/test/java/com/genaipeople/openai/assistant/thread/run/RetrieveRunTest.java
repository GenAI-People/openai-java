package com.genaipeople.openai.assistant.thread.run;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.ThreadRun;

public class RetrieveRunTest {

    @Test
    public void testRetrieveRun() throws Exception {
        ThreadRun threadRun = new ThreadRun(OpenAI.API_KEY, "thread_abc123");
        RunObject result = threadRun.retrieve("run_abc123").get();
        
        assertNotNull(result);
        assertEquals("run_abc123", result.getId());
        assertEquals("thread.run", result.getObject());
        assertEquals("thread_abc123", result.getThreadId());
        assertNotNull(result.getCreatedAt());
    }
} 