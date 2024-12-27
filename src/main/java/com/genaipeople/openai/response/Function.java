package com.genaipeople.openai.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Function {
    @JsonProperty("name")
    private String name;

    @JsonProperty("arguments")
    private String arguments;

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }   

    public String getArguments(){
        return this.arguments;
    }

    public void setArguments(String arguments){
        this.arguments = arguments;
    }
}
