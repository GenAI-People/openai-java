package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductCategory {
    COATS_AND_JACKETS("coats & jackets"),
    ACCESSORIES("accessories"),
    TOPS("tops"),
    JEANS_AND_TROUSERS("jeans & trousers"),
    SKIRTS_AND_DRESSES("skirts & dresses"),
    SHOES("shoes");

    private final String value;

    ProductCategory(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
} 