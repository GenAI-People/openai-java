package com.genaipeople.openai.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AutoChunkingStrategy implements ChunkingStrategy {

    @JsonProperty("type")
    private String type = "auto";

    @Override
    public String getType() {
        return type;
    }
}
