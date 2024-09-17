package com.genaipeople.openai.text;

import java.util.Map;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonSchema {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{1,64}$");
    private static final int MAX_NAME_LENGTH = 64;

    @JsonProperty("description")
    private String description;

    @JsonProperty("name")
    private String name;

    @JsonProperty("schema")
    private Map<String, Object> schema;

    @JsonProperty("strict")
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