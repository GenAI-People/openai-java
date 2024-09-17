package com.genaipeople.openai.text;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceTier {
    AUTO("auto"),
    DEFAULT("default");

    private final String value;

    ServiceTier(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}