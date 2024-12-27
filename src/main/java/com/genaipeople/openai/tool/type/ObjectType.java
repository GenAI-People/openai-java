package com.genaipeople.openai.tool.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ObjectType extends Type {
    @JsonProperty("properties")
    private Map<String, Type> properties;

    @JsonProperty("required")
    private List<String> requiredProperties;

    @JsonProperty("additionalProperties")
    private Boolean additionalProperties;

    public ObjectType() {
        super("object", null);
        this.requiredProperties = new ArrayList<>();
        this.properties = new HashMap<String, Type>();
        this.additionalProperties = false;
    }

    public ObjectType(String description) {
        super("object", description);
        this.requiredProperties = new ArrayList<>();
        this.properties = new HashMap<String, Type>();
        this.additionalProperties = false;
    }

    public Map<String, Type> getProperties() {
        return properties;
    }

    public void addProperty(String name, Type type, boolean required) {
        properties.put(name, type);
        if (required) {
            requiredProperties.add(name);
        }
    }

    public void setAdditionalProperties(boolean additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public boolean isAdditionalProperties() {
        return additionalProperties;
    }
}
