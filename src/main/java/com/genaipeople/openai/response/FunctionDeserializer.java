package com.genaipeople.openai.response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class FunctionDeserializer extends StdDeserializer<Function> {
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    public FunctionDeserializer() {
        this(null);
    }

    public FunctionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Function deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        Function function = new Function();
        
        if (node.has("name")) {
            function.setName(node.get("name").asText());
        }

        if (node.has("arguments")) {
            JsonNode arguments = node.get("arguments");
            if (arguments.isTextual()) {
                // Parse the JSON string into a JsonNode
                JsonNode argumentsNode = mapper.readTree(arguments.asText());
                Map<String, Object> argumentsMap = convertJsonNodeToMap(argumentsNode);
                @SuppressWarnings("unchecked")
                Map<String, Object> parametersMap = argumentsMap.containsKey("parameters") ? 
                    (Map<String, Object>) argumentsMap.get("parameters") : argumentsMap;
                function.setParameters(parametersMap);
            } else if (arguments.isObject()) {
                Map<String, Object> argumentsMap = convertJsonNodeToMap(arguments);
                @SuppressWarnings("unchecked")
                Map<String, Object> parametersMap = argumentsMap.containsKey("parameters") ? 
                    (Map<String, Object>) argumentsMap.get("parameters") : argumentsMap;
                function.setParameters(parametersMap);
            }
        }

        return function;
    }

    private Map<String, Object> convertJsonNodeToMap(JsonNode node) {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> fieldNames = node.fieldNames();
        
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = node.get(fieldName);
            
            if (fieldValue.isObject()) {
                map.put(fieldName, convertJsonNodeToMap(fieldValue));
            } else if (fieldValue.isTextual()) {
                map.put(fieldName, fieldValue.asText());
            } else if (fieldValue.isNumber()) {
                map.put(fieldName, fieldValue.numberValue());
            } else if (fieldValue.isBoolean()) {
                map.put(fieldName, fieldValue.booleanValue());
            } else if (fieldValue.isNull()) {
                map.put(fieldName, null);
            }
            // Add more type conversions as needed
        }
        
        return map;
    }
} 