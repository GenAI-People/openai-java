package com.genaipeople.openai.text;

public enum ToolChoice {
    AUTO("auto");

    private final String value;

    ToolChoice(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
