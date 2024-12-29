package com.genaipeople.openai.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ToolCallDeserializerTest {

    @Test
    public void testToolCallDeserialization() throws Exception {
        String json = "{"
            + "\"id\":\"call_123\","
            + "\"type\":\"function\","
            + "\"function\":{"
            + "  \"name\":\"get_current_weather\","
            + "  \"arguments\":\"{\\\"location\\\":\\\"Boston, MA\\\",\\\"unit\\\":\\\"celsius\\\"}\""
            + "}}";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ToolCall.class, new ToolCallDeserializer());
        module.addDeserializer(Function.class, new FunctionDeserializer());
        mapper.registerModule(module);

        ToolCall toolCall = mapper.readValue(json, ToolCall.class);
        
        assertEquals("call_123", toolCall.getId());
        assertEquals("function", toolCall.getType());
        assertNotNull(toolCall.getFunction());
        assertEquals("get_current_weather", toolCall.getFunction().getName());
        assertNotNull(toolCall.getFunction().getParameters());
        assertEquals("Boston, MA", toolCall.getFunction().getParameters().get("location"));
        assertEquals("celsius", toolCall.getFunction().getParameters().get("unit"));
    }
} 