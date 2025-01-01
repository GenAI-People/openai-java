package com.genaipeople.openai.assistant.thread;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.message.Message;
import com.genaipeople.openai.tool.Tool;

public class AdditionalMessage extends Message {
    @JsonProperty("attachments")
    private List<Attachment> attachments;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

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
}
