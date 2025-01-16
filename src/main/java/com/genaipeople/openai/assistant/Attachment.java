package com.genaipeople.openai.assistant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.tool.Tool;

public class Attachment {
    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("tools")
    private List<Tool> tools;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }
}
