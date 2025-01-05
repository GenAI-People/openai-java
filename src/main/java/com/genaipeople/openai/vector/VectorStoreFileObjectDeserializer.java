package com.genaipeople.openai.vector;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.assistant.AutoChunkingStrategy;
import com.genaipeople.openai.assistant.StaticChunkingStrategy;

import java.io.IOException;

public class VectorStoreFileObjectDeserializer extends JsonDeserializer<VectorStoreFileObject> {

    @Override
    public VectorStoreFileObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode node = mapper.readTree(jp);

        VectorStoreFileObject fileObject = new VectorStoreFileObject();
        
        if (node.has("chunking_strategy")) {
            JsonNode chunkingNode = node.get("chunking_strategy");
            String type = chunkingNode.get("type").asText();
            
            if ("static".equals(type)) {
                StaticChunkingStrategy strategy = new StaticChunkingStrategy();
                JsonNode staticNode = chunkingNode.get("static");
                int maxChunkSize = staticNode.get("max_chunk_size_tokens").asInt();
                int chunkOverlap = staticNode.get("chunk_overlap_tokens").asInt();
                strategy.setStaticSize(maxChunkSize, chunkOverlap);
                fileObject.setChunkingStrategy(strategy);
            }
            if("auto".equals(type)) {
                AutoChunkingStrategy strategy = new AutoChunkingStrategy();
                fileObject.setChunkingStrategy(strategy);
            }
        }

        if (node.has("id")) {
            fileObject.setId(node.get("id").asText());
        }

        if (node.has("vector_store_id")) {
            fileObject.setVectorStoreId(node.get("vector_store_id").asText());
        }

        if (node.has("status")) {
            fileObject.setStatus(node.get("status").asText());
        }

        if (node.has("last_error")) {
            JsonNode errorNode = node.get("last_error");
            com.genaipeople.openai.text.ErrorResponse errorResponse = 
                    new com.genaipeople.openai.text.ErrorResponse();
            com.genaipeople.openai.text.ErrorResponse.Error error = 
                errorResponse.new Error();
            
            if(errorNode.has("message")) {
                error.setMessage(errorNode.get("message").asText());
            }
            if(errorNode.has("type")) {
                error.setType(errorNode.get("type").asText());
            }
            if(errorNode.has("code")) {
                error.setCode(errorNode.get("code").asText());
            }
            if(errorNode.has("param")) {
                error.setParam(errorNode.get("param").asText());
            }
            fileObject.setLastError(error);
        }

        if (node.has("usage_bytes")) {
            fileObject.setUsageBytes(node.get("usage_bytes").asInt());
        }

        if (node.has("created_at")) {
            fileObject.setCreatedAt(node.get("created_at").asLong());
        }

        if (node.has("object")) {
            fileObject.setObject(node.get("object").asText());
        }

        return fileObject;
    }
}