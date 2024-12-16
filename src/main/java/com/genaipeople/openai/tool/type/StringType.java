package com.genaipeople.openai.tool.type;

import java.util.List;

public class StringType extends Type {

    public StringType(String description) {
        super("string", description);
    }

    public StringType(String description, List<String> enums) {
        super("string", description);
        this.enums = enums;
    }
}

