package com.genaipeople.openai.file;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FilePurpose {
    ASSISTANTS("assistants"),
    VISION("vision"),
    BATCH("batch"),
    FINE_TUNE("fine-tune");

    private final String value;

    FilePurpose(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
} 