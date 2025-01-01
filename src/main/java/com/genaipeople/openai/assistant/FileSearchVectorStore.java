package com.genaipeople.openai.assistant;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileSearchVectorStore {

    @JsonProperty("file_ids")
    private List<String> fileIds;

    @JsonProperty("chunking_strategy")
    private ChunkingStrategy chunkingStrategy;

    /*
     * Set of 16 key-value pairs that can be attached to a vector store. 
     * This can be useful for storing additional information about 
     * the vector store in a structured format. Keys can be a maximum of 
     * 64 characters long and values can be a maximum of 512 characters long.
     */
    private Map<String, String> metadata;

    public List<String> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }

    public ChunkingStrategy getChunkingStrategy() {
        return chunkingStrategy;
    }

    public void setChunkingStrategy(ChunkingStrategy chunkingStrategy) {
        this.chunkingStrategy = chunkingStrategy;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    /*
     * Set of 16 key-value pairs that can be attached to a vector store. 
     * This can be useful for storing additional information about 
     * the vector store in a structured format. Keys can be a maximum of 
     * 64 characters long and values can be a maximum of 512 characters long.
     */
    public void setMetadata(Map<String, String> metadata) {
        if (metadata.size() > 16) {
            throw new IllegalArgumentException("Metadata can only contain up to 16 key-value pairs");
        }
        for (Map.Entry<String, String> entry : metadata.entrySet()) {
            if (entry.getKey().length() > 64 || entry.getValue().length() > 512) {
                throw new IllegalArgumentException("Key or value in metadata is too long");
            }
        }
        this.metadata = metadata;
    }
}
