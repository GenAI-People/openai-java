package com.genaipeople.openai.assistant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileSearchResource implements ToolResource {
    @Override
    public String getToolName() {
        return "file_search";
    }

    @JsonProperty("vector_store_ids")
    private List<String> vectorStoreIds;

    @JsonProperty("vector_stores")
    private List<FileSearchVectorStore> vectorStores;

    public List<String> getVectorStoreIds() {
        return vectorStoreIds;
    }

    public void setVectorStoreIds(List<String> vectorStoreIds) {
        this.vectorStoreIds = vectorStoreIds;
    }

    public List<FileSearchVectorStore> getVectorStores() {
        return vectorStores;
    }

    public void setVectorStores(List<FileSearchVectorStore> vectorStores) {
        this.vectorStores = vectorStores;
    }
}
