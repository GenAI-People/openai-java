package com.genaipeople.openai.vector;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VectorStoreListResponse<T> {
    @JsonProperty("object")
    private String object;

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("first_id")
    private String firstId;

    @JsonProperty("last_id")
    private String lastId;

    @JsonProperty("has_more")
    private Boolean hasMore;

    // Getters and setters
    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public List<T> getData() { return data; }
    public void setData(List<T> data) { this.data = data; }

    public String getFirstId() { return firstId; }
    public void setFirstId(String firstId) { this.firstId = firstId; }

    public String getLastId() { return lastId; }
    public void setLastId(String lastId) { this.lastId = lastId; }

    public Boolean getHasMore() { return hasMore; }
    public void setHasMore(Boolean hasMore) { this.hasMore = hasMore; }
} 