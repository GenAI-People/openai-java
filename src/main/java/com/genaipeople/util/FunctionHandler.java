package com.genaipeople.util;

import java.util.List;

import com.genaipeople.openai.tool.Function;

public class FunctionHandler {
    private List<Function> functions;

    public FunctionHandler(String functionJson) {
    }

    public Function getFunction(String name){
        return this.functions.stream().filter(function -> function.getName().equals(name)).findFirst().orElse(null);
    }

    public void setFunctions(List<Function> functions){
        this.functions = functions;
    }
}
