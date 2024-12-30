package com.genaipeople.openai.file;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDeleteResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("deleted")
    private boolean deleted;

    @JsonProperty("object")
    private String object;

    public String getId() {
        return id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getObject() {
        return object;
    }
}
