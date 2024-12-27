package com.genaipeople.openai.tool;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class FunctionSerializer extends StdSerializer<Function> {
    
    public FunctionSerializer() {
        this(null);
    }

    public FunctionSerializer(Class<Function> t) {
        super(t);
    }

    @Override
    public void serialize(Function function, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        
        // Write the type field
        gen.writeStringField("type", "function");
        
        // Write the function object
        gen.writeObjectFieldStart("function");
        
        // Write all function properties
        gen.writeStringField("name", function.getName());
        
        if (function.getDescription() != null) {
            gen.writeStringField("description", function.getDescription());
        }
        
        if (function.getParameters() != null) {
            gen.writeObjectField("parameters", function.getParameters());
        }

        if (function.getStrict() != null) {
            gen.writeBooleanField("strict", function.getStrict());
        }
        
        gen.writeEndObject(); // end function object
        gen.writeEndObject(); // end main object
    }
} 