package com.genaipeople.openai.assistant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = FileSearchResourceSerializer.class)
public class FileSearchResource implements ToolResource {
    private List<String> vectorStoreIds;

    public FileSearchResource(List<String> vectorStoreIds) {
        this.vectorStoreIds = vectorStoreIds;
    }

    public List<String> getVectorStoreIds() {
        return vectorStoreIds;
    }

    public void setVectorStoreIds(List<String> vectorStoreIds) {
        this.vectorStoreIds = vectorStoreIds;
    }

    @Override
    public String getToolName() {
        return "file_search";
    }
}
