package com.genaipeople.openai.assistant.thread.run.step;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.ThreadRunStep;
import com.genaipeople.openai.assistant.thread.run.RunStepObject;

public class RetrieveRunStepTest {

    @Test
    public void testRetrieveRunStep() throws Exception {
        ThreadRunStep runStep = new ThreadRunStep(OpenAI.API_KEY, "thread_abc123", "run_abc123");
        RunStepObject result = runStep.retrieve("step_abc123").get();
        
        assertNotNull(result);
        assertEquals("step_abc123", result.getId());
        assertEquals("thread.run.step", result.getObject());
        assertEquals("thread_abc123", result.getThreadId());
        assertEquals("run_abc123", result.getRunId());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getStatus());
    }

    @Test
    public void testRetrieveRunStepWithDetails() throws Exception {
        ThreadRunStep runStep = new ThreadRunStep(OpenAI.API_KEY, "thread_abc123", "run_abc123");
        RunStepObject result = runStep.retrieve("step_abc123").get();
        
        assertNotNull(result);
        assertNotNull(result.getStepDetails());
        assertNotNull(result.getType());
        if (result.getStatus().equals("completed")) {
            assertNotNull(result.getCompletedAt());
            assertNotNull(result.getUsage());
        }
    }
} 