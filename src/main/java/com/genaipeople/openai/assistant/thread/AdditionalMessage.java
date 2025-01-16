package com.genaipeople.openai.assistant.thread;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.assistant.Attachment;
import com.genaipeople.openai.message.Message;

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
}
