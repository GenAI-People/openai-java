package com.genaipeople.openai.message.content;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.genaipeople.openai.message.Content;

public class ContentSerializer extends JsonSerializer<Content> {
    @Override
    public void serialize(Content content, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (content instanceof ImageContent) {
            serializeImageContent((ImageContent) content, gen);
        }
        else if (content instanceof TextContent) {
            serializeTextContent((TextContent) content, gen);
        }
    }
    
    private void serializeImageContent(ImageContent imageContent, JsonGenerator gen) throws IOException {
        gen.writeStartArray();
        
        // Write the text content
        gen.writeStartObject();
        gen.writeStringField("type", "text");
        gen.writeStringField("text", imageContent.getContent());
        gen.writeEndObject();

        // Write the image URL
        gen.writeStartObject();
        gen.writeStringField("type", "image_url");
        gen.writeObjectFieldStart("image_url");
        gen.writeStringField("url", imageContent.getImageUrl());
        gen.writeEndObject();
        gen.writeEndObject();

        gen.writeEndArray();
    }

    private void serializeTextContent(TextContent textContent, JsonGenerator gen) throws IOException {
        gen.writeString(textContent.getContent());
    }
}