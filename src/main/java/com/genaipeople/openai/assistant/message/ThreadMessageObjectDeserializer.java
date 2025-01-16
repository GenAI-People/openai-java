package com.genaipeople.openai.assistant.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.assistant.Attachment;

public class ThreadMessageObjectDeserializer extends JsonDeserializer<ThreadMessageObject> {
    @Override
    public ThreadMessageObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);
        
        ThreadMessageObject message = new ThreadMessageObject();
        
        if (node.has("id")) message.setId(node.get("id").asText());
        if (node.has("created_at")) message.setCreatedAt(node.get("created_at").asInt());
        if (node.has("thread_id")) message.setThreadId(node.get("thread_id").asText());
        if (node.has("role")) message.setRole(node.get("role").asText());
        if (node.has("assistant_id")) message.setAssistantId(node.get("assistant_id").asText());
        if (node.has("run_id")) message.setRunId(node.get("run_id").asText());
        
        if (node.has("content")) {
            JsonNode contentArray = node.get("content");
            List<ThreadMessageContent> contentList = new ArrayList<>();
            
            for (JsonNode contentNode : contentArray) {
                String type = contentNode.get("type").asText();
                ThreadMessageContent content = null;
                
                switch (type) {
                    case "text":
                        ThreadTextMessageContent textContent = new ThreadTextMessageContent(
                                contentNode.get("text").get("value").asText());
                        content = textContent;
                        break;
                        
                    case "image_file":
                        ThreadImageFileMessageContent imageFileContent = new ThreadImageFileMessageContent(
                                contentNode.get("image_file").get("file_id").asText());
                        if (contentNode.get("image_file").has("detail")) {
                            imageFileContent.setDetail(contentNode.get("image_file").get("detail").asText());
                        }
                        content = imageFileContent;
                        break;
                        
                    case "image_url":
                        ThreadImageUrlMessageContent imageUrlContent = new ThreadImageUrlMessageContent(
                                contentNode.get("image_url").get("url").asText());
                        if (contentNode.get("image_url").has("detail")) {
                            imageUrlContent.setDetail(contentNode.get("image_url").get("detail").asText());
                        }
                        content = imageUrlContent;
                        break;
                        
                    case "refusal":
                        ThreadRefusalMessageContent refusalContent = new ThreadRefusalMessageContent(
                            contentNode.get("refusal").get("reason").asText());
                        content = refusalContent;
                        break;
                }
                
                if (content != null) {
                    contentList.add(content);
                }
            }
            message.setContent(contentList);
        }
        
        if (node.has("attachments")) {
            List<Attachment> attachments = new ArrayList<>();
            for (JsonNode attachmentNode : node.get("attachments")) {
                attachments.add(mapper.treeToValue(attachmentNode, Attachment.class));
            }
            message.setAttachments(attachments);
        }
        
        if (node.has("metadata")) {
            @SuppressWarnings("unchecked")
            Map<String,String> metadata = mapper.convertValue(node.get("metadata"), Map.class);
            message.setMetadata(metadata);
        }
        
        return message;
    }
} 