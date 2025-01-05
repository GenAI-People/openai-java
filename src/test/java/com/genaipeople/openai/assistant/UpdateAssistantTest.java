package com.genaipeople.openai.assistant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.Assistant;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.assistant.response.AssistantObject;
import com.genaipeople.openai.tool.CodeInterpreter;
import java.util.List;

public class UpdateAssistantTest {

    @Test
    public void testUpdateAssistant() throws Exception {
        Assistant assistant = new Assistant(OpenAI.API_KEY);
        AssistantRequest request = new AssistantRequest(
            "Updated instructions",
            "Updated Math Tutor",
            List.of(new CodeInterpreter()),
            "gpt-4o"
        );
        
        AssistantObject result = assistant.update("asst_abc123", request).get();
        
        assertNotNull(result);
        assertEquals("Updated Math Tutor", result.getName());
        assertEquals("Updated instructions", result.getInstructions());
    }
} 