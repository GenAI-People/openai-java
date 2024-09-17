package com.genaipeople.openai.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.Role;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    @JsonProperty("role")
    private Role role;

    @JsonProperty("content")
    private String content;

    @JsonProperty("name")
    private String name; // optional

    @JsonProperty("refusal")
    private String refusal;

    public Message(String content, Role role) {
        this.role = role;
        this.content = content;
    }

    // Required for Jackson deserialization
    public Message() {}

    // Getters
    public Role getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setRole(Role role) {
        this.role = role;
    }

    public void setContent(String content) {
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