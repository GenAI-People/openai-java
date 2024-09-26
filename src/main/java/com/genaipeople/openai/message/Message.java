package com.genaipeople.openai.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.genaipeople.openai.Role;
import com.genaipeople.openai.message.content.ContentDeserializer;
import com.genaipeople.openai.message.content.ContentSerializer;
import com.genaipeople.openai.message.content.ImageContent;
import com.genaipeople.openai.message.content.TextContent;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    @JsonProperty("role")
    private Role role;

    @JsonSerialize(using = ContentSerializer.class)
    @JsonDeserialize(using = ContentDeserializer.class)
    @JsonProperty("content")
    private Content content;

    @JsonProperty("name")
    private String name; // optional

    @JsonProperty("refusal")
    private String refusal;

    public Message(String content, Role role) {
        this.role = role;
        this.content = new TextContent(content);
    }

    public Message(String content, String imageUrl, Role role) {
        this.role = role;
        this.content = new ImageContent(content, imageUrl);
    }

    // Required for Jackson deserialization
    public Message() {}

    // Getters
    public Role getRole() {
        return role;
    }

    public Content getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setRole(Role role) {
        this.role = role;
    }

    // Set the content of the message
    public void setContent(Content content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRefusal(String refusal) {
        this.refusal = refusal;
    }

    public String getRefusal() {
        return refusal;
    }
}

