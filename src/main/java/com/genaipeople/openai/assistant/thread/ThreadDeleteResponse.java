package com.genaipeople.openai.assistant.thread;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThreadDeleteResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("deleted")
    private boolean deleted;

    @JsonProperty("object")
    private String object;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public boolean getDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }
} 