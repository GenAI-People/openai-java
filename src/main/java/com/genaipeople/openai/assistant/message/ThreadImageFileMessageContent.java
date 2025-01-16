package com.genaipeople.openai.assistant.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ThreadImageFileMessageContentSerializer.class)
@JsonDeserialize(using = ThreadImageFileMessageContentDeserializer.class)
public class ThreadImageFileMessageContent extends ThreadMessageContent {
    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("detail")
    private String detail;

    public ThreadImageFileMessageContent(String fileId) {
        super("image_file");
        this.fileId = fileId;
    }

    public String getFileId() { return fileId; }
    public void setFileId(String fileId) { this.fileId = fileId; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
}