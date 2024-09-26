package com.genaipeople.openai.message.content;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.genaipeople.openai.message.Content;

public class ContentDeserializer extends JsonDeserializer<Content> {
    @Override
    public Content deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        switch (p.currentToken()) {
            case VALUE_STRING:
                return createTextContent(p.getText());
            case START_ARRAY:
                return deserializeImageContent(p);
            default:
                throw new IllegalArgumentException("Invalid content type: " + p.currentToken());
        }
    }

    private Content deserializeImageContent(JsonParser p) throws IOException {
        String text = null;
        String imageUrl = null;

        while (p.nextToken() != JsonToken.END_ARRAY) {
            if (p.currentToken() == JsonToken.START_OBJECT) {
                String type = null;
                while (p.nextToken() != JsonToken.END_OBJECT) {
                    String fieldName = p.getCurrentName();
                    p.nextToken();
                    if ("type".equals(fieldName)) {
                        type = p.getText();
                    } else if ("text".equals(fieldName) && "text".equals(type)) {
                        text = p.getText();
                    } else if ("image_url".equals(fieldName) && "image_url".equals(type)) {
                        // Handle nested image_url object
                        if (p.currentToken() == JsonToken.START_OBJECT) {
                            while (p.nextToken() != JsonToken.END_OBJECT) {
                                if ("url".equals(p.getCurrentName())) {
                                    p.nextToken();
                                    imageUrl = p.getText();
                                }
                            }
                        }
                    }
                }
            }
        }

        if (text != null && imageUrl != null) {
            return new ImageContent(text, imageUrl);
        } else if (text != null) {
            return new TextContent(text);
        } else {
            throw new IllegalArgumentException("Invalid content format");
        }
    }

    private TextContent createTextContent(String text) {
        return new TextContent(text);
    }
}