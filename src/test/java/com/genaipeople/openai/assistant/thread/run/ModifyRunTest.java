package com.genaipeople.openai.assistant.thread.run;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.ThreadRun;
import java.util.HashMap;
import java.util.Map;

public class ModifyRunTest {

    @Test
    public void testModifyRun() throws Exception {
        ThreadRun threadRun = new ThreadRun(OpenAI.API_KEY, "thread_abc123");
        Map<String, String> metadata = new HashMap<>();
        metadata.put("modified", "true");
        metadata.put("user", "abc123");
        
        RunObject result = threadRun.modify("run_abc123", metadata).get();
        
        assertNotNull(result);
        assertNotNull(result.getMetadata());
        assertEquals("true", result.getMetadata().get("modified"));
        assertEquals("abc123", result.getMetadata().get("user"));
    }
} 