package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ProductSearchParameters {
    @JsonProperty("categories")
    private List<ProductCategory> categories;

    @JsonProperty("colors")
    private List<ProductColor> colors;

    @JsonProperty("keywords")
    private List<String> keywords;

    @JsonProperty("price_range")
    private PriceRange priceRange;

    @JsonProperty("limit")
    private Integer limit = 5; // Default value as per requirement

    // Getters and Setters
    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }

    public List<ProductColor> getColors() {
        return colors;
    }

    public void setColors(List<ProductColor> colors) {
        this.colors = colors;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
} 