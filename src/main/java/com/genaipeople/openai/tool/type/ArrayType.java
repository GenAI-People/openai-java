package com.genaipeople.openai.tool.type;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ArrayTypeSerializer.class)
public class ArrayType extends Type {
    private Class<?> itemType;
    
    public ArrayType(Class<?> itemType, String description) {
        super("array", description);
        this.itemType = itemType;
    }

    public Class<?> getItemType() {
        return itemType;
    }
}
