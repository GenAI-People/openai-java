package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CodeInterpreter extends Tool {
    @JsonProperty("type")
    private String type = "code_interpreter";

    public String getType() { return type; }
}
