package com.genaipeople.openai.tool.type;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Type {
    @JsonProperty("type")
    private String type;

    @JsonProperty("description")
    private String description;

    @JsonProperty("enum")
    protected List<? extends Object> enums;

    public Type(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }   

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }   

    public List<? extends Object> getEnums() {
        return enums;
    }

    public void setEnums(List<? extends Object> enums) {
        this.enums = enums;
    }
}
