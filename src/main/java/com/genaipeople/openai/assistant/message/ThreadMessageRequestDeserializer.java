package com.genaipeople.openai.assistant.message;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.genaipeople.openai.Role;
import com.genaipeople.openai.assistant.Attachment;

public class ThreadMessageRequestDeserializer extends StdDeserializer<ThreadMessageRequest> {
    
    public ThreadMessageRequestDeserializer() {
        this(null);
    }

    public ThreadMessageRequestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ThreadMessageRequest deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        Role role = Role.valueOf(node.get("role").asText().toUpperCase());
        
        ThreadMessageRequest request;
        if (node.get("content").isTextual()) {
            request = new ThreadMessageRequest(role, node.get("content").asText());
        } else if (node.get("content").isObject()) {
            request = new ThreadMessageRequest(role, Arrays.asList(convertContent(node.get("content"))));
        } else {
            throw new IllegalArgumentException("Invalid content type");
        }

        if (node.has("attachments")) {
            ObjectMapper mapper = (ObjectMapper) jp.getCodec();
            request.setAttachments(mapper.convertValue(node.get("attachments"),
                mapper.getTypeFactory().constructCollectionType(List.class, Attachment.class)));
        }
        if (node.has("metadata")) {
            ObjectMapper mapper = (ObjectMapper) jp.getCodec();
            request.setMetadata(mapper.convertValue(node.get("metadata"),
                mapper.getTypeFactory().constructMapType(Map.class, String.class, String.class)));
        }

        return request;
    }

    private ThreadMessageContent convertContent(JsonNode contentNode) {
        String type = contentNode.get("type").asText();
        switch (type) {
            case "text":
                return new ThreadTextMessageContent(contentNode.get("text").asText());
            case "refusal":
                return new ThreadRefusalMessageContent(contentNode.get("refusal").asText());
            case "image_file":
                JsonNode imageFile = contentNode.get("image_file");
                ThreadImageFileMessageContent fileContent = new ThreadImageFileMessageContent(imageFile.get("file_id").asText());
                if (imageFile.has("detail")) {
                    fileContent.setDetail(imageFile.get("detail").asText());
                }
                return fileContent;
            case "image_url":
                JsonNode imageUrl = contentNode.get("image_url");
                ThreadImageUrlMessageContent urlContent = new ThreadImageUrlMessageContent(imageUrl.get("url").asText());
                if (imageUrl.has("detail")) {
                    urlContent.setDetail(imageUrl.get("detail").asText());
                }
                return urlContent;
            default:
                throw new IllegalArgumentException("Unknown content type: " + type);
        }
    }
} 