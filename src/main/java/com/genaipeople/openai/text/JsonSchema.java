package com.genaipeople.openai.text;

import java.util.Map;
import java.util.regex.Pattern;

public class JsonSchema {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{1,64}$");
    private static final int MAX_NAME_LENGTH = 64;

    private String description;
    private String name;
    private Map<String, Object> schema;
    private Boolean strict;

    // Constructor
    public JsonSchema(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid name. Name must contain only a-z, A-Z, 0-9, underscores, or dashes, and be at most 64 characters long.");
        }
        this.name = name;
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getSchema() {
        return schema;
    }

    public void setSchema(Map<String, Object> schema) {
        this.schema = schema;
    }

    public Boolean getStrict() {
        return strict;
    }

    public void setStrict(Boolean strict) {
        this.strict = strict;
    }

    private boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches() && name.length() <= MAX_NAME_LENGTH;
    }
}