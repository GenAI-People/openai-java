package com.genaipeople.openai.file;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileList {
    @JsonProperty("data")
    private List<FileObject> data;

    @JsonProperty("object")
    private String object;

    @JsonProperty("has_more")
    private boolean hasMore;

    @JsonProperty("first_id")
    private String firstId;

    @JsonProperty("last_id")
    private String lastId;

    // Getters
    public List<FileObject> getData() {
        return data;
    }

    public String getObject() {
        return object;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public String getFirstId() {
        return firstId;
    }

    public String getLastId() {
        return lastId;
    }
}
