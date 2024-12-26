package com.genaipeople.openai.tool.type;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SchemaGeneratorTest {
    
    static class CartItem {
        @JsonProperty("product_id")
        private String productId;
        
        @JsonProperty("quantity")
        private int quantity;
    }
    
    static class Cart {
        @JsonProperty("items")
        private List<CartItem> items;
    }
    
    @Test
    public void testGenerateCartSchema() throws Exception {
        ObjectType schema = SchemaGenerator.generateSchema(Cart.class);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(schema);
        
        System.out.println(json);
        
        // Basic structure checks
        assertTrue(json.contains("\"type\":\"object\""));
        assertTrue(json.contains("\"items\":{\"type\":\"array\""));
        
        // Check cart item properties
        assertTrue(json.contains("\"product_id\":{\"type\":\"string\""));
        assertTrue(json.contains("\"quantity\":{\"type\":\"integer\""));
    }
    
    @Test
    public void testSimpleObject() throws Exception {
        static class SimpleObject {
            @JsonProperty("name")
            private String name;
            
            @JsonProperty("age")
            private int age;
            
            @JsonProperty("height")
            private double height;
        }
        
        ObjectType schema = SchemaGenerator.generateSchema(SimpleObject.class);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(schema);
        
        System.out.println(json);
        
        assertTrue(json.contains("\"name\":{\"type\":\"string\""));
        assertTrue(json.contains("\"age\":{\"type\":\"integer\""));
        assertTrue(json.contains("\"height\":{\"type\":\"number\""));
    }
} 