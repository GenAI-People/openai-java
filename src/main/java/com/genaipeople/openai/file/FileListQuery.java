package com.genaipeople.openai.file;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileListQuery {
    @JsonProperty("purpose")
    private FilePurpose purpose;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("order")
    private String order;

    @JsonProperty("after")
    private String after;

    public FileListQuery() {
    }

    public FileListQuery(FilePurpose purpose, Integer limit, FileListOrder order, String after) {
        this.purpose = purpose;
        this.limit = limit;
        this.order = order.getValue();
        this.after = after;
    }

    public FilePurpose getPurpose() {
        return purpose;
    }

    public Integer getLimit() {
        return limit;
    }

    public String getOrder() {
        return order;
    }

    public String getAfter() {
        return after;
    }
}
