package com.genaipeople.openai.tool;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileSearch extends Tool {
    @JsonProperty("type")
    private String type = "file_search";

    public String getType() { return type; }

    @JsonProperty("vector_store_ids")
    private List<String> vectorStoreIds;

    public List<String> getVectorStoreIds() { return vectorStoreIds; }
    public void setVectorStoreIds(List<String> vectorStoreIds) { this.vectorStoreIds = vectorStoreIds; }
}
