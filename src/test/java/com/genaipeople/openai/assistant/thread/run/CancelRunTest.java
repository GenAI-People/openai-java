package com.genaipeople.openai.assistant.thread.run;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.ThreadRun;

public class CancelRunTest {

    @Test
    public void testCancelRun() throws Exception {
        ThreadRun threadRun = new ThreadRun(OpenAI.API_KEY, "thread_abc123");
        RunObject result = threadRun.cancel("run_abc123").get();
        
        assertNotNull(result);
        assertEquals("run_abc123", result.getId());
        assertNotNull(result.getCancelledAt());
        assertEquals("cancelled", result.getStatus());
    }
} 