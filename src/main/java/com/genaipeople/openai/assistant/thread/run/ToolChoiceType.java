package com.genaipeople.openai.assistant.thread.run;

public enum ToolChoiceType {
    none("none"),
    auto("auto"),
    required("required");

    private final String value;

    ToolChoiceType(String value) { this.value = value; }
    public String getValue() { return value; }
}
