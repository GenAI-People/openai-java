package com.genaipeople.openai.assistant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.Assistant;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.assistant.response.AssistantObject;

public class DeleteAssistantTest {

    @Test
    public void testDeleteAssistant() throws Exception {
        Assistant assistant = new Assistant(OpenAI.API_KEY);
        AssistantObject result = assistant.delete("asst_abc123").get();
        
        assertNotNull(result);
    }
} 