package com.genaipeople.openai.text;

public class ResponseFormat {
    private ResponseType type;
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
