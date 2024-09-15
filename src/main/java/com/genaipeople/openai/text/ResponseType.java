package com.genaipeople.openai.text;

public enum ResponseType {
    Text("text"),
    JsonObject("json_object"),
    JsonSchema("json_schema");

    private final String value;

    ResponseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
