package com.genaipeople.openai.assistant.thread.run.step;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.ThreadRunStep;
import com.genaipeople.openai.assistant.thread.run.RunStepObject;
import java.util.List;

public class ListRunStepsTest {

    @Test
    public void testListRunSteps() throws Exception {
        ThreadRunStep runStep = new ThreadRunStep(OpenAI.API_KEY, "thread_abc123", "run_abc123");
        List<RunStepObject> result = runStep.list().get();
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        RunStepObject firstStep = result.get(0);
        assertEquals("thread.run.step", firstStep.getObject());
        assertEquals("thread_abc123", firstStep.getThreadId());
        assertEquals("run_abc123", firstStep.getRunId());
    }

    @Test
    public void testListRunStepsWithPagination() throws Exception {
        ThreadRunStep runStep = new ThreadRunStep(OpenAI.API_KEY, "thread_abc123", "run_abc123");
        List<RunStepObject> result = runStep.list(5, "desc", null, null).get();
        
        assertNotNull(result);
        assertTrue(result.size() <= 5);
        result.forEach(step -> {
            assertNotNull(step.getId());
            assertNotNull(step.getCreatedAt());
            assertEquals("thread.run.step", step.getObject());
        });
    }
} 