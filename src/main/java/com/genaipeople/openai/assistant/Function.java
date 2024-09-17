package com.genaipeople.openai.assistant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Function {
    @JsonProperty("name")
    private String name;

    @JsonProperty("arguments")
    private String arguments;

    // No-args constructor for JSON deserialization
    public Function() {}

    public Function(String name, String arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getArguments() {
        return arguments;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }
}