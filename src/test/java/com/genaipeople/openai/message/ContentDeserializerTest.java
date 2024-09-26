package com.genaipeople.openai.message;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.genaipeople.openai.message.content.ContentDeserializer;
import com.genaipeople.openai.message.content.ImageContent;
import com.genaipeople.openai.message.content.TextContent;

class ContentDeserializerTest {

    @Test
    public void testImageContentDeserialization() throws IOException {
        String jsonInput = "[{\"type\":\"text\",\"text\":\"Sample text\"},{\"type\":\"image_url\",\"image_url\":{\"url\":\"http://example.com/image.jpg\"}}]";
        ContentDeserializer deserializer = new ContentDeserializer();
        JsonParser parser = new ObjectMapper().getFactory().createParser(jsonInput);
        parser.nextToken(); // Move to START_ARRAY token

        Content content = deserializer.deserialize(parser, null);
        assertNotNull(content);
        assertTrue(content instanceof ImageContent);

        ImageContent imageContent = (ImageContent) content;
        assertEquals("Sample text", imageContent.getContent());
        assertEquals("http://example.com/image.jpg", imageContent.getImageUrl());
    }

    @Test
    public void testTextContentDeserialization() throws IOException {
        String jsonInput = "\"Hello, world!\"";
        ContentDeserializer deserializer = new ContentDeserializer();
        JsonParser parser = new ObjectMapper().getFactory().createParser(jsonInput);
        parser.nextToken(); // Move to VALUE_STRING token

        Content content = deserializer.deserialize(parser, null);
        assertNotNull(content);
        assertTrue(content instanceof TextContent);

        TextContent textContent = (TextContent) content;
        assertEquals("Hello, world!", textContent.getContent());
    }

    @Test
    public void testDeserializeNewImageContent() throws Exception {
        String json = "[{\"type\":\"text\",\"text\":\"What's in this image?\"},{\"type\":\"image_url\",\"image_url\":{\"url\":\"https://example.com/image.jpg\"}}]";
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addDeserializer(Content.class, new ContentDeserializer()));
        
        Content content = mapper.readValue(json, Content.class);
        
        assertTrue(content instanceof ImageContent);
        ImageContent imageContent = (ImageContent) content;
        assertEquals("What's in this image?", imageContent.getContent());
        assertEquals("https://example.com/image.jpg", imageContent.getImageUrl());
    }
}