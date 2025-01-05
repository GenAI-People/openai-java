package com.genaipeople.openai.vector;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.vector.VectorStoreObject.ExpirationPolicy;

public class VectorUpdateRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("expires_after")
    private ExpirationPolicy expiresAfter;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpirationPolicy getExpiresAfter() {
        return expiresAfter;
    }

    public void setExpiresAfter(ExpirationPolicy expiresAfter) {
        this.expiresAfter = expiresAfter;
    }
}
