package com.genaipeople.openai.tool.type;

public enum Name {
    OBJECT("object"),
    ARRAY("array"),
    STRING("string"),
    NUMBER("number"),
    INTEGER("integer"),
    BOOLEAN("boolean"),
    NULL("null"),
    ENUM("enum");
    private final String value;

    Name(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
