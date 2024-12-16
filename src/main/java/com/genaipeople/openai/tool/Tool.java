package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tool {
    @JsonProperty("type")
    private String type;

    @JsonProperty("function")
    private Function function;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
