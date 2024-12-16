package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.tool.type.ObjectType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Function {
    @JsonProperty("description")
    private String description;

    @JsonProperty("name")
    private String name;

    @JsonProperty("parameters")
    private ObjectType parameters;

    @JsonProperty("strict")
    private Boolean strict;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.matches("^[a-zA-Z0-9_-]{1,64}$")) {
            throw new IllegalArgumentException("Name must only contain a-z, A-Z, 0-9, underscores and dashes, with max length of 64");
        }
        this.name = name;
    }

    public ObjectType getParameters() {
        return parameters;
    }

    public void setParameters(ObjectType parameters) {
        this.parameters = parameters;
    }

    public Boolean getStrict() {
        return strict;
    }

    public void setStrict(Boolean strict) {
        this.strict = strict;
    }
}
