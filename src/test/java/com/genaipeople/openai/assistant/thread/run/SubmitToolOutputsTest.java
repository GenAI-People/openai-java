package com.genaipeople.openai.assistant.thread.run;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.ThreadRun;
import java.util.List;
import java.util.Map;

public class SubmitToolOutputsTest {

    @Test
    public void testSubmitToolOutputs() throws Exception {
        ThreadRun threadRun = new ThreadRun(OpenAI.API_KEY, "thread_abc123");
        List<Map<String, String>> toolOutputs = List.of(
            Map.of(
                "tool_call_id", "call_abc123",
                "output", "The weather is sunny and 72°F"
            )
        );
        
        RunObject result = threadRun.submitToolOutputs("run_abc123", toolOutputs).get();
        
        assertNotNull(result);
        assertEquals("run_abc123", result.getId());
        assertEquals("completed", result.getStatus());
    }
} 