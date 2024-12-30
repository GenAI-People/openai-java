package com.genaipeople.openai.file;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class FileObjectDeserializer extends StdDeserializer<FileObject> {
    
    public FileObjectDeserializer() {
        this(null);
    }

    public FileObjectDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public FileObject deserialize(JsonParser jp, DeserializationContext ctxt) 
        throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        FileObject fileObject = new FileObject();
        
        fileObject.setId(getTextSafely(node, "id"));
        fileObject.setObject(getTextSafely(node, "object"));
        fileObject.setBytes(getLongSafely(node, "bytes"));
        fileObject.setCreatedAt(getLongSafely(node, "created_at"));
        fileObject.setFilename(getTextSafely(node, "filename"));
        fileObject.setPurpose(getTextSafely(node, "purpose"));
        fileObject.setStatus(getTextSafely(node, "status"));
        fileObject.setStatusDetails(getTextSafely(node, "status_details"));

        return fileObject;
    }

    private String getTextSafely(JsonNode node, String fieldName) {
        return node.has(fieldName) && !node.get(fieldName).isNull() 
            ? node.get(fieldName).asText() 
            : null;
    }

    private Long getLongSafely(JsonNode node, String fieldName) {
        return node.has(fieldName) && !node.get(fieldName).isNull() 
            ? node.get(fieldName).asLong() 
            : null;
    }
} 