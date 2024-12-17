package com.genaipeople.openai.tool.type;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = TypeSerializer.class)
public class Type<T> {
    @JsonProperty("type")
    private String type;

    @JsonProperty("description")
    private String description;

    @JsonProperty("enum")
    protected List<T> enums;

    private Boolean required;

    @JsonProperty("properties")
    private Map<String, Type<?>> properties;

    public Type(Map<String, Type<?>> properties) {
        this.type = Name.OBJECT.getValue();
        this.properties = properties;
    }

    public Type(Name name, String description, Boolean required) {
        this.type = name.getValue();
        this.description = description;
        this.required = required;
    }
    
    public Type(Name name, String description, List<T> enums, Boolean required) {
        this.type = name.getValue();
        this.description = description;
        this.enums = enums;
        this.required = required;
    }
    public Boolean getRequired() {
        return required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }   

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }   

    public List<? extends Object> getEnums() {
        return enums;
    }

    public void setEnums(List<T> enums) {
        this.enums = enums;
    }

    public Map<String, Type<?>> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Type<?>> properties) {
        this.properties = properties;
    }

    public void addProperty(String string, Type<?> type) {
        if (this.properties == null) {
            this.properties = new HashMap<String, Type<?>>();
        }
        this.properties.put(string, type);
    }
}
