package com.genaipeople.openai.assistant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.Assistant;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.assistant.response.AssistantObject;

public class RetrieveAssistantTest {

    @Test
    public void testRetrieveAssistant() throws Exception {
        Assistant assistant = new Assistant(OpenAI.API_KEY);
        AssistantObject result = assistant.retrieve("asst_abc123").get();
        
        assertNotNull(result);
        assertEquals("asst_abc123", result.getId());
        assertEquals("assistant", result.getObject());
    }
} 