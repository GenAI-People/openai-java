package com.genaipeople.openai;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ToolType {
    FUNCTION("function");

    private final String value;

    ToolType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
