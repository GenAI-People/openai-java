package com.genaipeople.openai.tool.type;

import java.util.List;

public class NumericType<T extends Number> extends Type {

    public NumericType(String type, String description) {
        super(type, description);
    }

    public NumericType(String type, String description, List<T> enums) {
        super(type, description);
        this.enums = enums;
    }
}
