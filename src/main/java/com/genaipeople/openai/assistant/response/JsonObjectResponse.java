package com.genaipeople.openai.assistant.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonObjectResponse implements ResponseFormat {
    @JsonProperty("type")
    private String type = "json_object";

    public String getType() { return type; }
}
