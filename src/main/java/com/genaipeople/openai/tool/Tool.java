package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Tool {
    @JsonProperty("type")
    private String type;
}
