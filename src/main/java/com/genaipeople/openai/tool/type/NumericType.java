package com.genaipeople.openai.tool.type;

import java.util.List;

public class NumericType<T extends Number> extends Type {

    public NumericType(Class<T> typeClass, String description) {
        super(typeClass.equals(Integer.class) ? "integer" : "number", description);
    }

    public NumericType(Class<T> typeClass, String description, List<T> enums) {
        super(typeClass.equals(Integer.class) ? "integer" : "number", description);
        this.enums = enums;
    }
}
