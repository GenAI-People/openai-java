package com.genaipeople.openai.file;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileObject {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("bytes")
    private long bytes;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("status")
    private String status;

    @JsonProperty("status_details")
    private String statusDetails;

    // Getters
    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public long getBytes() {
        return bytes;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getFilename() {
        return filename;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    // Add setters
    public void setId(String id) {
        this.id = id;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }
}
