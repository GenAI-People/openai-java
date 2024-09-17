package com.genaipeople.openai.assistant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolCall {
    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private ToolType type;

    @JsonProperty("function")
    private Function function;

    public ToolCall(String id, ToolType type, Function function) {
        this.id = id;
        this.type = type;
        this.function = function;
    }
    
    // Getters
    public String getId() {
        return id;
    }

    public ToolType getType() {
        return type;
    }

    public Function getFunction() {
        return function;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setType(ToolType type) {
        this.type = type;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
