package com.genaipeople.openai.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ChunkingStrategy {

    @JsonProperty("type")
    String getType();
}
