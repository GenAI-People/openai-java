package com.genaipeople.openai.tool.type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class ObjectTypeTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testBasicObjectSerialization() throws Exception {
        Type<String> stringType = new Type<String>(Name.STRING, "Test String", true);
        
        String json = objectMapper.writeValueAsString(stringType);
        
        assertTrue(json.contains("\"type\":\"string\""));
        assertTrue(json.contains("\"description\":\"Test String\""));
    }

    @Test
    void testObjectWithProperties() throws Exception {
        Map<String, Type<?>> properties = new HashMap<>();
        
        // Add a string property
        properties.put("name", new Type<String>(Name.STRING, "User's name", true));
        
        // Add a numeric property
        properties.put("age", new Type<Integer>(Name.INTEGER, "User's age", true));
        
        Type<Object> objectType = new Type<Object>(properties);
        
        String json = objectMapper.writeValueAsString(objectType);
        System.out.println(json);
        assertTrue(json.contains("\"properties\":{"));
        assertTrue(json.contains("\"name\":{\"type\":\"string\""));
        assertTrue(json.contains("\"age\":{\"type\":\"integer\""));
    }

    @Test
    void testObjectWithPropertiesAndEnums() throws Exception {
        Map<String, Type<?>> properties = new HashMap<>();
        
        // Add a string property
        properties.put("name", new Type<String>(Name.STRING, "User's name", Arrays.asList("John", "Jane", "Doe"), true));
        
        // Add a numeric property
        properties.put("age", new Type<Integer>(Name.INTEGER, "User's age", Arrays.asList(18, 19, 20), true));
        
        Type<Object> objectType = new Type<Object>(properties);
        
        String json = objectMapper.writeValueAsString(objectType);
        System.out.println(json);
        assertTrue(json.contains("\"properties\":{"));
        assertTrue(json.contains("\"name\":{\"type\":\"string\""));
        assertTrue(json.contains("\"age\":{\"type\":\"integer\""));
        assertTrue(json.contains("\"John\""));
        assertTrue(json.contains("\"Jane\""));
        assertTrue(json.contains("\"Doe\""));
        assertTrue(json.contains("18"));
        assertTrue(json.contains("19"));
        assertTrue(json.contains("20"));
    }

    @Test
    void testProductSearchSchema() throws Exception {
        // Create root object schema
        Type<Object> schema = new Type<Object>(Name.OBJECT, "Product search parameters", false);
        
        List<String> categories = Arrays.asList("coats & jackets", "accessories", "tops", "jeans & trousers", "skirts & dresses");
        Type<String> categoriesType = new Type<String>(Name.ARRAY, "Product category", categories, true);
        schema.addProperty("categories", categoriesType);
        // Categorie
        List<String> colors = Arrays.asList("black", "white", "brown", "red", "blue", "green", "orange", "yellow", "pink", "gold", "silver");
        Type<String> colorsType = new Type<String>(Name.ARRAY, "Color", colors, true);
        schema.addProperty("colors", colorsType);

        List<String> keywords = Arrays.asList("coat", "jacket", "accessory", "top", "jean", "trouser", "skirt", "dress", "shoe");
        Type<String> keywordsType = new Type<String>(Name.ARRAY, "Keyword", keywords, true);
        schema.addProperty("keywords", keywordsType);

        Type<Object> priceRange = new Type<Object>(Name.OBJECT, "Price range constraints", true);
        priceRange.addProperty("min", new Type<Double>(Name.NUMBER, "Minimum price", Arrays.asList(0.0, 1000.0), true));
        priceRange.addProperty("max", new Type<Double>(Name.NUMBER, "Maximum price", Arrays.asList(0.0, 1000.0), true));
        schema.addProperty("price_range", priceRange);

        // Limit
        schema.addProperty("limit", new Type<Integer>(Name.INTEGER, "Maximum number of products to return (default: 5)", true));
        
        String json = objectMapper.writeValueAsString(schema);
        System.out.println(json);
        // Verify structure
        assertTrue(json.contains("\"categories\":{\"type\":\"array\""));
        assertTrue(json.contains("\"colors\":{\"type\":\"array\""));
        assertTrue(json.contains("\"keywords\":{\"type\":\"array\""));
        assertTrue(json.contains("\"price_range\":{\"type\":\"object\""));
        assertTrue(json.contains("\"limit\":{\"type\":\"integer\""));
        
        // Verify enums
        assertTrue(json.contains("\"coat\""));
        assertTrue(json.contains("\"jacket\""));
        assertTrue(json.contains("\"accessory\""));
        assertTrue(json.contains("\"top\""));
        assertTrue(json.contains("\"jean\""));
        assertTrue(json.contains("\"trouser\""));
        assertTrue(json.contains("\"skirt\""));
        assertTrue(json.contains("\"dress\""));
        assertTrue(json.contains("\"shoe\""));
        assertTrue(json.contains("\"black\""));
        assertTrue(json.contains("\"silver\""));
        // Verify required fields
        assertTrue(json.contains("\"required\":[\"keywords\",\"limit\",\"price_range\",\"categories\",\"colors\"]"));

    }
}