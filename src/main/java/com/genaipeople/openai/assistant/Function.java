package com.genaipeople.openai.assistant;

public class Function{
    private String name;
    private String arguments;

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