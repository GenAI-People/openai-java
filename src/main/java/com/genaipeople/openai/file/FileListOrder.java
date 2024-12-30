package com.genaipeople.openai.file;

public enum FileListOrder {
    ASC("asc"),
    DESC("desc");

    private final String value;

    FileListOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
