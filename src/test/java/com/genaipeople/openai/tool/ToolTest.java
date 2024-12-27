package com.genaipeople.openai.tool;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.tool.type.ObjectType;
import com.genaipeople.openai.tool.type.StringType;

public class ToolTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testWeatherFunctionSerialization() throws Exception {
        // Create the function
        Function function = new Function();
        function.setName("get_current_weather");
        function.setDescription("Get the current weather in a given location");

        // Create parameters
        ObjectType parameters = new ObjectType();
        
        // Add location parameter
        StringType locationType = new StringType(
            "The city and state, e.g. San Francisco, CA",
            null
        );
        parameters.addProperty("location", locationType, true);

        // Add unit parameter
        StringType unitType = new StringType(
            null,
            Arrays.asList("celsius", "fahrenheit")
        );
        parameters.addProperty("unit", unitType, false);

        function.setParameters(parameters);

        // Create tool with function
        Function tool = new Function();
        tool.setName("testFunction");

        // Serialize and verify
        String json = objectMapper.writeValueAsString(tool);
        System.out.println(json);
        // Verify structure
        assertTrue(json.contains("\"type\":\"function\""));
        assertTrue(json.contains("\"function\":{"));
        assertTrue(json.contains("\"name\":\"testFunction\""));
    }
} 