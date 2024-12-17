package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.tool.type.Type;

import java.util.Map;

public class Parameter {
    @JsonProperty("type")
    private String type;

    @JsonProperty("properties")
    private Map<String, Type<?>> properties;

    @JsonProperty("additionalProperties")
    private Boolean additionalProperties;

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Type<?>> getProperties() {
        return properties;
    }

    public void addProperty(String name, Type<?> value) {
        properties.put(name, value);
    }

    public Boolean getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Boolean additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
