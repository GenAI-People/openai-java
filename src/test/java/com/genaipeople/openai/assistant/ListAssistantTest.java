package com.genaipeople.openai.assistant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.Assistant;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.assistant.response.AssistantObject;

public class ListAssistantTest {

    @Test
    public void testListAssistants() throws Exception {
        Assistant assistant = new Assistant(OpenAI.API_KEY);
        AssistantObject result = assistant.list(20, "desc", null, null).get();
        
        assertNotNull(result);
    }

    @Test
    public void testListAssistantsWithPagination() throws Exception {
        Assistant assistant = new Assistant(OpenAI.API_KEY);
        AssistantObject result = assistant.list(5, "asc", "asst_abc123", null).get();
        
        assertNotNull(result);
    }
} 