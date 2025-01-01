package com.genaipeople.openai.assistant.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonSchema {
    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("schema")
    private Object schema;
    @JsonProperty("strict")
    private Boolean strict;

    // Getters and setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Object getSchema() { return schema; }
    public void setSchema(Object schema) { this.schema = schema; }
    public Boolean getStrict() { return strict; }
    public void setStrict(Boolean strict) { this.strict = strict; }
}
