package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductColor {
    BLACK("black"),
    WHITE("white"),
    BROWN("brown"),
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    ORANGE("orange"),
    YELLOW("yellow"),
    PINK("pink"),
    GOLD("gold"),
    SILVER("silver");

    private final String value;

    ProductColor(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
} 