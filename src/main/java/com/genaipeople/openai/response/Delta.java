package com.genaipeople.openai.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.genaipeople.openai.Role;
import com.genaipeople.openai.message.Content;
import com.genaipeople.openai.message.content.ContentDeserializer;
import com.genaipeople.openai.message.content.ContentSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Delta {
    @JsonProperty("role")
    private Role role;

    @JsonSerialize(using = ContentSerializer.class)
    @JsonDeserialize(using = ContentDeserializer.class)
    @JsonProperty("content")
    private Content content;

    @JsonProperty("refusal")
    private String refusal;     

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getRefusal() {
        return refusal;
    }

    public void setRefusal(String refusal) {
        this.refusal = refusal;
    }
}