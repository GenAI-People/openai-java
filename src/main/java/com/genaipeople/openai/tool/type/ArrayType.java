package com.genaipeople.openai.tool.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArrayType<T extends Type> extends Type {

    @JsonProperty("items")
    private T item;
    
    public ArrayType(String description) {
        super("array", description);
    }

    public ArrayType(String description, T item) {
        super("array", description);
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
