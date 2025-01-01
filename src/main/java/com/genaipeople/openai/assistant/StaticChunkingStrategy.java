package com.genaipeople.openai.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StaticChunkingStrategy implements ChunkingStrategy {

    @JsonProperty("type")
    private String type = "static";

    @JsonProperty("static")
    private StaticSize static_size;

    @Override
    public String getType() {
        return type;
    }

    public StaticSize getStaticSize() {
        return static_size;
    }

    public void setStaticSize(StaticSize static_size) {
        this.static_size = static_size;
    }

    class StaticSize {
        @JsonProperty("max_chunk_size_tokens")
        private int maxChunkSizeTokens;

        @JsonProperty("chunk_overlap_tokens")
        private int chunkOverlapTokens;

        public int getMaxChunkSizeTokens() {
            return maxChunkSizeTokens;
        }

        public int getChunkOverlapTokens() {
            return chunkOverlapTokens;
        }

        /*
         * The maximum number of tokens in each chunk. The default value is 800. The minimum value is 100 and the maximum value is 4096.
         * The number of tokens that overlap between chunks. The default value is 400. Note that the overlap must not exceed half of max_chunk_size_tokens.
         */
        public StaticSize(int maxChunkSizeTokens, int chunkOverlapTokens) {
            if (maxChunkSizeTokens < 100 || maxChunkSizeTokens > 4096) {
                throw new IllegalArgumentException("max_chunk_size_tokens must be between 100 and 4096");
            }
            if (chunkOverlapTokens < 0 || chunkOverlapTokens > maxChunkSizeTokens / 2) {
                throw new IllegalArgumentException("chunk_overlap_tokens must be between 0 and max_chunk_size_tokens / 2");
            }
            this.maxChunkSizeTokens = maxChunkSizeTokens;
            this.chunkOverlapTokens = chunkOverlapTokens;
        }
    }
}
