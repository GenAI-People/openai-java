package com.genaipeople.openai.vector;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.assistant.ChunkingStrategy;
import com.genaipeople.openai.text.ErrorResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = VectorStoreFileObjectDeserializer.class)
public class VectorStoreFileObject {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("usage_bytes")
    private Integer usageBytes;

    @JsonProperty("vector_store_id")
    private String vectorStoreId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("last_error")
    private ErrorResponse.Error lastError;

    @JsonProperty("chunking_strategy")
    private ChunkingStrategy chunkingStrategy;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    public Integer getUsageBytes() { return usageBytes; }
    public void setUsageBytes(Integer usageBytes) { this.usageBytes = usageBytes; }

    public String getVectorStoreId() { return vectorStoreId; }
    public void setVectorStoreId(String vectorStoreId) { this.vectorStoreId = vectorStoreId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public ErrorResponse.Error getLastError() { return lastError; }
    public void setLastError(ErrorResponse.Error lastError) { this.lastError = lastError; }

    public ChunkingStrategy getChunkingStrategy() { return chunkingStrategy; }
    public void setChunkingStrategy(ChunkingStrategy chunkingStrategy) { this.chunkingStrategy = chunkingStrategy; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VectorStoreFileObject that = (VectorStoreFileObject) obj;
        return Objects.equals(id, that.id);
    }
} 
