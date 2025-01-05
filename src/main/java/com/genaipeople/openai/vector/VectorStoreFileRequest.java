package com.genaipeople.openai.vector;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.assistant.ChunkingStrategy;

public class VectorStoreFileRequest {
    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("chunking_strategy")
    private ChunkingStrategy chunkingStrategy;

    // Getters and setters
    public String getFileId() { return fileId; }
    public void setFileId(String fileId) { this.fileId = fileId; }

    public ChunkingStrategy getChunkingStrategy() { return chunkingStrategy; }
    public void setChunkingStrategy(ChunkingStrategy chunkingStrategy) { this.chunkingStrategy = chunkingStrategy; }
}
