package com.genaipeople.openai.assistant.thread.run;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class StepDetailsDeserializer extends StdDeserializer<StepDetails> {
    
    public StepDetailsDeserializer() {
        this(null);
    }

    public StepDetailsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public StepDetails deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        if (!node.has("type")) {
            throw new IOException("Missing 'type' field in StepDetails");
        }

        String type = node.get("type").asText();
        
        switch (type) {
            case "message_creation":
                MessageCreationStep messageStep = new MessageCreationStep();
                JsonNode messageCreationNode = node.get("message_creation");
                if (messageCreationNode != null && messageCreationNode.has("message_id")) {
                    messageStep.setMessageId(messageCreationNode.get("message_id").asText());
                }
                return messageStep;

            case "tool_calls":
                ToolCallsStep toolStep = new ToolCallsStep();
                if (node.has("tool_calls")) {
                    toolStep.setToolCalls(((com.fasterxml.jackson.databind.ObjectMapper)jp.getCodec()).readValue(
                        node.get("tool_calls").traverse(),
                        ((com.fasterxml.jackson.databind.ObjectMapper)jp.getCodec()).getTypeFactory().constructCollectionType(
                            List.class,
                            com.genaipeople.openai.response.ToolCall.class
                        )
                    ));
                }
                return toolStep;

            default:
                throw new IOException("Unknown step details type: " + type);
        }
    }
} 