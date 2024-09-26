package com.genaipeople.openai.message;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.message.content.ContentSerializer;
import com.genaipeople.openai.message.content.ImageContent;
import com.genaipeople.openai.message.content.TextContent;

class ContentSerializerTest {

    @Test
    public void testImageContentSerialization() throws IOException {
        ImageContent imageContent = new ImageContent("Sample text", "http://example.com/image.jpg");
        ContentSerializer serializer = new ContentSerializer();
        StringWriter writer = new StringWriter();
        JsonGenerator gen = new ObjectMapper().getFactory().createGenerator(writer);

        serializer.serialize(imageContent, gen, null);
        gen.close();

        String jsonOutput = writer.toString();
        System.out.println(jsonOutput);
        assertNotNull(jsonOutput);
        assertTrue(jsonOutput.contains("\"type\":\"text\""));
        assertTrue(jsonOutput.contains("\"text\":\"Sample text\""));
        assertTrue(jsonOutput.contains("\"type\":\"image_url\""));
        assertTrue(jsonOutput.contains("\"url\":\"http://example.com/image.jpg\""));
        
        // Verify the array structure
        assertTrue(jsonOutput.startsWith("["));
        assertTrue(jsonOutput.endsWith("]"));
    }

    @Test
    public void testTextContentSerialization() throws IOException {
        TextContent textContent = new TextContent("Hello, world!");
        ContentSerializer serializer = new ContentSerializer();
        StringWriter writer = new StringWriter();
        JsonGenerator gen = new ObjectMapper().getFactory().createGenerator(writer);

        serializer.serialize(textContent, gen, null);
        gen.close();

        String jsonOutput = writer.toString();
        System.out.println(jsonOutput);
        assertNotNull(jsonOutput);
        assertEquals("\"Hello, world!\"", jsonOutput);
    }
}