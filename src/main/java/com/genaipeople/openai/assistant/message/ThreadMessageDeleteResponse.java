package com.genaipeople.openai.assistant.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThreadMessageDeleteResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object = "thread.message.deleted";

    @JsonProperty("deleted")
    private boolean deleted;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}