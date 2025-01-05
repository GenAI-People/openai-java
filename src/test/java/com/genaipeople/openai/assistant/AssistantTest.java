package com.genaipeople.openai.assistant;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.Assistant;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.assistant.response.AssistantObject;
import com.genaipeople.openai.tool.CodeInterpreter;
import com.genaipeople.openai.tool.FileSearch;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AssistantTest {
    @Test
    public void testCreateAssistantWithMathTutor() throws Exception {
        // Arrange
        String expectedJson = "{\n" +
            "  \"id\": \"asst_abc123\",\n" +
            "  \"object\": \"assistant\",\n" +
            "  \"created_at\": 1698984975,\n" +
            "  \"name\": \"Math Tutor\",\n" +
            "  \"description\": null,\n" +
            "  \"model\": \"gpt-4o-mini\",\n" +
            "  \"instructions\": \"You are a personal math tutor. When asked a question, write and run Python code to answer the question.\",\n" +
            "  \"tools\": [{\"type\": \"code_interpreter\"}],\n" +
            "  \"metadata\": {},\n" +
            "  \"top_p\": 1.0,\n" +
            "  \"temperature\": 1.0,\n" +
            "  \"response_format\": \"auto\"\n" +
            "}";
        
        // Act
        Assistant assistant = new Assistant(OpenAI.API_KEY);
        AssistantObject result = assistant.create(
            new AssistantRequest(
                "You are a personal math tutor. When asked a question, write and run Python code to answer the question.",
                "Math Tutor",
                List.of(new CodeInterpreter()),
                "gpt-4o-mini"
            )
        ).get();
        
        // Assert
        ObjectMapper mapper = new ObjectMapper();
        AssistantObject expected = mapper.readValue(expectedJson, AssistantObject.class);
        assertNotNull(result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getInstructions(), result.getInstructions());
        assertEquals(expected.getModel(), result.getModel());
        assertEquals(expected.getTools().size(), result.getTools().size());
        assertEquals(expected.getTools().get(0).getType(), result.getTools().get(0).getType());
        assertEquals("code_interpreter", result.getTools().get(0).getType());
        assertEquals(expected.getTemperature(), result.getTemperature());
        assertEquals(expected.getTopP(), result.getTopP());
        assertEquals(expected.getResponseFormat(), result.getResponseFormat());
        assertEquals(expected.getMetadata(), result.getMetadata());
        assertNotNull(expected.getCreatedAt());
        assertEquals(expected.getDescription(), result.getDescription());
        assertNotNull(expected.getId());
        assertEquals(expected.getModel(), result.getModel());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getObject(), result.getObject());
    }

    @Test
    public void testCreateAssistantWithFileSearchTool() throws Exception {
        // Arrange
        String expectedJson = "{\n" +
            "  \"id\": \"asst_abc123\",\n" +
            "  \"object\": \"assistant\",\n" +
            "  \"created_at\": 1698984975,\n" +
            "  \"name\": \"Math Tutor\",\n" +
            "  \"description\": null,\n" +
            "  \"model\": \"gpt-4o-mini\",\n" +
            "  \"instructions\": \"You are a personal math tutor. When asked a question, write and run Python code to answer the question.\",\n" +
            "  \"tools\": [{\"type\": \"file_search\"}],\n" +
            "  \"metadata\": {},\n" +
            "  \"temperature\": 1.0,\n" +
            "  \"top_p\": 1.0,\n" +
            "  \"response_format\": \"auto\"\n" +
            "}";
        
        // Act
        Assistant assistant = new Assistant(OpenAI.API_KEY);
        AssistantObject result = assistant.create(
            new AssistantRequest(
                "You are a personal math tutor. When asked a question, write and run Python code to answer the question.",
                "Math Tutor",
                List.of(new FileSearch()),
                "gpt-4o-mini"
            )
        ).get();

        // Assert
        ObjectMapper mapper = new ObjectMapper();
        AssistantObject expected = mapper.readValue(expectedJson, AssistantObject.class);
        assertNotNull(result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getInstructions(), result.getInstructions());
        assertEquals(expected.getModel(), result.getModel());
        assertEquals(expected.getTools().size(), result.getTools().size());
        assertEquals(expected.getTools().get(0).getType(), result.getTools().get(0).getType());
        assertEquals("file_search", result.getTools().get(0).getType());
        assertEquals(expected.getTemperature(), result.getTemperature());
        assertEquals(expected.getTopP(), result.getTopP());
        assertEquals(expected.getResponseFormat(), result.getResponseFormat());
        assertEquals(expected.getMetadata(), result.getMetadata());
        assertNotNull(expected.getCreatedAt());
        assertEquals(expected.getDescription(), result.getDescription());
        assertNotNull(expected.getId());
        assertEquals(expected.getModel(), result.getModel());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getObject(), result.getObject());
    }

    @Test
    public void testCreateAssistantWithFileSearch() throws Exception {
        // Arrange
        String expectedJson = "{\n" +
            "  \"id\": \"asst_abc123\",\n" +
            "  \"object\": \"assistant\",\n" +
            "  \"created_at\": 1698984975,\n" +
            "  \"name\": null,\n" +
            "  \"description\": null,\n" +
            "  \"model\": \"gpt-4o\",\n" +
            "  \"instructions\": \"You are an HR bot, and you have access to files to answer employee questions about company policies.\",\n" +
            "  \"tools\": [{\"type\": \"file_search\"}],\n" +
            "  \"tool_resources\": {\"file_search\": {\"vector_store_ids\": [\"vs_123\"]}},\n" +
            "  \"metadata\": {},\n" +
            "  \"temperature\": 1.0,\n" +
            "  \"top_p\": 1.0,\n" +
            "  \"response_format\": \"auto\"\n" +
            "}";
        
        // Act
        Assistant assistant = new Assistant(OpenAI.API_KEY);
        AssistantRequest request = new AssistantRequest(
                "You are an HR bot, and you have access to files to answer employee questions about company policies.",
                null, // name is optional
                List.of(new FileSearch()),
                "gpt-4o"
            );
        request.setToolResources(new FileSearchResource(List.of("vs_123")));
        AssistantObject result = assistant.create(
            request
        ).get();
        
        // Assert
        ObjectMapper mapper = new ObjectMapper();
        AssistantObject expected = mapper.readValue(expectedJson, AssistantObject.class);
        assertNotNull(result.getId());
        assertEquals(expected.getInstructions(), result.getInstructions());
        assertEquals(expected.getModel(), result.getModel());
        assertEquals(expected.getTools().size(), result.getTools().size());
        assertEquals(expected.getTools().get(0).getType(), result.getTools().get(0).getType());
        assertEquals("file_search", result.getTools().get(0).getType());
    }

    
}
