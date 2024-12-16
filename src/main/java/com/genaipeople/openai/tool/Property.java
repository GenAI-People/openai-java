package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Property<T> {
    @JsonProperty("description")
    private String description;

    @JsonProperty("type")
    private T type;

    private Boolean required;
    
    public Property(String description, Boolean required) {
        this.description = description;
        this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
