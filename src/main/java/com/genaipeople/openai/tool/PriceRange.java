package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceRange {
    @JsonProperty("min")
    private Double min;

    @JsonProperty("max")
    private Double max;

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
} 