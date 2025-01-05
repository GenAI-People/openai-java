package com.genaipeople.openai.vector;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.assistant.ChunkingStrategy;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VectorCreateRequest {
    @JsonProperty("file_ids")
    private List<String> fileIds;

    @JsonProperty("name")
    private String name;

    @JsonProperty("expires_after")
    private ExpirationPolicy expiresAfter;

    @JsonProperty("chunking_strategy")
    private ChunkingStrategy chunkingStrategy;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    // Getters and setters
    public List<String> getFileIds() { return fileIds; }
    public void setFileIds(List<String> fileIds) { this.fileIds = fileIds; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ExpirationPolicy getExpiresAfter() { return expiresAfter; }
    public void setExpiresAfter(ExpirationPolicy expiresAfter) { this.expiresAfter = expiresAfter; }

    public ChunkingStrategy getChunkingStrategy() { return chunkingStrategy; }
    public void setChunkingStrategy(ChunkingStrategy chunkingStrategy) { this.chunkingStrategy = chunkingStrategy; }

    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }

    public static class ExpirationPolicy {
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
