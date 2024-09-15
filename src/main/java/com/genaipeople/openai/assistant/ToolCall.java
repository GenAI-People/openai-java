package com.genaipeople.openai.assistant;

public class ToolCall {
    private String id;
    private ToolType type;
    private Function function;

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
