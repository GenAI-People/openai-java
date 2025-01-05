package com.genaipeople.openai.vector;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VectorStoreObject {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("usage_bytes")
    private Integer usageBytes;

    @JsonProperty("file_counts")
    private FileCounts fileCounts;

    @JsonProperty("status")
    private String status;

    @JsonProperty("expires_after")
    private ExpirationPolicy expiresAfter;

    @JsonProperty("expires_at")
    private Long expiresAt;

    @JsonProperty("last_active_at")
    private Long lastActiveAt;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getUsageBytes() { return usageBytes; }
    public void setUsageBytes(Integer usageBytes) { this.usageBytes = usageBytes; }

    public FileCounts getFileCounts() { return fileCounts; }
    public void setFileCounts(FileCounts fileCounts) { this.fileCounts = fileCounts; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public ExpirationPolicy getExpiresAfter() { return expiresAfter; }
    public void setExpiresAfter(ExpirationPolicy expiresAfter) { this.expiresAfter = expiresAfter; }

    public Long getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Long expiresAt) { this.expiresAt = expiresAt; }

    public Long getLastActiveAt() { return lastActiveAt; }
    public void setLastActiveAt(Long lastActiveAt) { this.lastActiveAt = lastActiveAt; }

    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }

    public class FileCounts {
        @JsonProperty("in_progress")
        private Integer inProgress;

        @JsonProperty("completed")
        private Integer completed;

        @JsonProperty("failed")
        private Integer failed;

        @JsonProperty("cancelled")
        private Integer cancelled;

        @JsonProperty("total")
        private Integer total;

        public Integer getInProgress() { return inProgress; }
        public void setInProgress(Integer inProgress) { this.inProgress = inProgress; }

        public Integer getCompleted() { return completed; }
        public void setCompleted(Integer completed) { this.completed = completed; }

        public Integer getFailed() { return failed; }
        public void setFailed(Integer failed) { this.failed = failed; }
    }

    public class ExpirationPolicy {
        /*
         * anchor
            string

            Required
            Anchor timestamp after which the expiration policy applies. Supported anchors: last_active_at.

            days
            integer
         */
        @JsonProperty("anchor")
        private String anchor;

        @JsonProperty("days")
        private Integer days;

        public String getAnchor() { return anchor; }
        public void setAnchor(String anchor) { this.anchor = anchor; }

        public Integer getDays() { return days; }
        public void setDays(Integer days) { this.days = days; }
    } 

} 