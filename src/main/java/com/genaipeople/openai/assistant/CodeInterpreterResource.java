package com.genaipeople.openai.assistant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CodeInterpreterResource implements ToolResource {
    @Override
    public String getToolName() {
        return "code_interpreter";
    }

    @JsonProperty("file_ids")
    private List<String> fileIds;

    public List<String> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }
}
