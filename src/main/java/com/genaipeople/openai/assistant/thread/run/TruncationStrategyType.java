package com.genaipeople.openai.assistant.thread.run;


public enum TruncationStrategyType {
    AUTO("auto"),
    LAST_MESSAGES("last_messages");

    private final String value;

    TruncationStrategyType(String value) { this.value = value; }
    public String getValue() { return value; }
}
