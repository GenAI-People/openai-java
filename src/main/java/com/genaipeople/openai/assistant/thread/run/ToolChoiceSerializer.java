package com.genaipeople.openai.assistant.thread.run;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.genaipeople.openai.tool.Function;

public class ToolChoiceSerializer extends StdSerializer<ToolChoice> {
    
    public ToolChoiceSerializer() {
        this(null);
    }

    public ToolChoiceSerializer(Class<ToolChoice> t) {
        super(t);
    }

    @Override
    public void serialize(ToolChoice value, JsonGenerator gen, SerializerProvider provider) 
        throws IOException {
        if (value.getTool() == null) {
            // Simple string value (e.g. "auto", "none")
            gen.writeString(value.getType().toString());
        } else {
            gen.writeStartObject();
            gen.writeStringField("type", value.getType().toString());
            if (value.getTool() != null && value.getTool() instanceof Function) {
                gen.writeObjectFieldStart("function");
                gen.writeStringField("name", ((Function)value.getTool()).getName());
                gen.writeEndObject();
            }
            gen.writeEndObject();
        }
    }
} 