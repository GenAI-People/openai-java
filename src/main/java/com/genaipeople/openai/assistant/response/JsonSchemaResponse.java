package com.genaipeople.openai.assistant.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonSchemaResponse implements ResponseFormat {
    @JsonProperty("type")
    private String type;
    @JsonProperty("json_schema")
    private JsonSchema jsonSchema;

    // Getters and setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public JsonSchema getJsonSchema() { return jsonSchema; }
    public void setJsonSchema(JsonSchema jsonSchema) { this.jsonSchema = jsonSchema; }
}
