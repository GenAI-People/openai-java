package com.genaipeople.openai.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;
import java.util.HashMap;


@JsonDeserialize(using = FunctionDeserializer.class)    
public class Function {
    @JsonProperty("name")
    private String name;

    @JsonProperty("arguments")
    private String arguments;
    
    private Map<String, Object> parameters = new HashMap<>();

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

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, Object> argumentMap) {
        this.parameters = argumentMap;
    }
}
