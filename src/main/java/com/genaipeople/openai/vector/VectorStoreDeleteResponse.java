package com.genaipeople.openai.vector;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * {
  id: "vs_abc123",
  object: "vector_store.deleted",
  deleted: true
}
 */
public class VectorStoreDeleteResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("deleted")
    private Boolean deleted;

    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}
