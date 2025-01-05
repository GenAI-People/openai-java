package com.genaipeople.openai.tool;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileSearch extends Tool {
    @JsonProperty("type")
    private String type = "file_search";

    public String getType() { return type; }
}
