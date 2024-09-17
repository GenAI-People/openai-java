package com.genaipeople.openai.text;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseFormat {
    @JsonProperty("type")
    private ResponseType type;

    @JsonProperty("json_schema")
    private JsonSchema jsonSchema;

    // Constructor
    public ResponseFormat(ResponseType type) {
        this.type = type;
    }

    // Getters and setters
    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public JsonSchema getJsonSchema() {
        return jsonSchema;
    }

    public void setJsonSchema(JsonSchema jsonSchema) {
        this.jsonSchema = jsonSchema;
    }
}
