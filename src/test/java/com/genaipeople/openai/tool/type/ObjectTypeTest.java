package com.genaipeople.openai.tool.type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class ObjectTypeTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testBasicObjectSerialization() throws Exception {
        ObjectType objectType = new ObjectType("Test object");
        
        String json = objectMapper.writeValueAsString(objectType);
        
        assertTrue(json.contains("\"type\":\"object\""));
        assertTrue(json.contains("\"description\":\"Test object\""));
    }

    @Test
    void testObjectWithProperties() throws Exception {
        ObjectType objectType = new ObjectType("Object with properties");
        Map<String, Type> properties = new HashMap<>();
        
        // Add a string property
        properties.put("name", new StringType("User's name"));
        
        // Add a numeric property
        properties.put("age", new NumericType<Integer>("integer", "User's age"));
        
        objectType.addProperty("name", new StringType("User's name"), true);
        objectType.addProperty("age", new NumericType<Integer>("integer", "User's age"), true);
        
        String json = objectMapper.writeValueAsString(objectType);
        
        assertTrue(json.contains("\"properties\":{"));
        assertTrue(json.contains("\"name\":{\"type\":\"string\""));
        assertTrue(json.contains("\"age\":{\"type\":\"integer\""));
    }

    @Test
    void testObjectWithRequiredFields() throws Exception {
        ObjectType objectType = new ObjectType("Object with required fields");
        Map<String, Type> properties = new HashMap<>();
        properties.put("id", new StringType("User ID"));
        properties.put("email", new StringType("Email address"));
        
        objectType.addProperty("id", new StringType("User ID"), true);
        objectType.addProperty("email", new StringType("Email address"), true);
        
        String json = objectMapper.writeValueAsString(objectType);
        
        assertTrue(json.contains("\"required\":["));
        assertTrue(json.contains("\"id\""));
        assertTrue(json.contains("\"email\""));
    }

    @Test
    void testObjectWithAdditionalProperties() throws Exception {
        ObjectType objectType = new ObjectType("Object with additional properties");
        objectType.setAdditionalProperties(false);
        
        String json = objectMapper.writeValueAsString(objectType);
        
        assertTrue(json.contains("\"additionalProperties\":false"));
    }

    @Test
    void testProductSearchSchema() throws Exception {
        ObjectType objectType = new ObjectType(null);
        objectType.setAdditionalProperties(false);
        
        // Categories array with enum
        StringType categoryType = new StringType(null);
        categoryType.setEnums(Arrays.asList("coats & jackets", "accessories", "tops", 
            "jeans & trousers", "skirts & dresses"));
        ArrayType categoriesArray = new ArrayType(String.class,
            "categories that could be a match"
        );
        objectType.addProperty("categories", categoriesArray, true);
        
        // Colors array with enum
        StringType colorType = new StringType(null);
        colorType.setEnums(Arrays.asList("black", "white", "brown", "red", "blue", 
            "green", "orange", "yellow", "pink", "gold", "silver"));
        ArrayType colorsArray = new ArrayType(String.class,
            "colors that could be a match, empty array if N/A"
        );
        objectType.addProperty("colors", colorsArray, true);
        
        // Keywords array
        ArrayType keywordsArray = new ArrayType(String.class,
            "keywords that should be present in the item title or description"
        );
        objectType.addProperty("keywords", keywordsArray, true);
        
        // Price range object
        ObjectType priceRange = new ObjectType(null);
        priceRange.setAdditionalProperties(false);
        priceRange.addProperty("min", new NumericType<>("number", null), true);
        priceRange.addProperty("max", new NumericType<>("number", null), true);
        objectType.addProperty("price_range", priceRange, true);
        
        // Limit
        objectType.addProperty("limit", new NumericType<>("integer", 
            "The maximum number of products to return, use 5 by default if nothing is specified by the user"), 
            true);
        
        String json = objectMapper.writeValueAsString(objectType);
        System.out.println(json);
        
        // Verify structure
        assertTrue(json.contains("\"categories\":{\"type\":\"array\""));
        assertTrue(json.contains("\"colors\":{\"type\":\"array\""));
        assertTrue(json.contains("\"keywords\":{\"type\":\"array\""));
        assertTrue(json.contains("\"price_range\":{\"type\":\"object\""));
        assertTrue(json.contains("\"limit\":{\"type\":\"integer\""));
        
        // Verify enums
        assertTrue(json.contains("\"coats & jackets\""));
        assertTrue(json.contains("\"accessories\""));
        assertTrue(json.contains("\"black\""));
        assertTrue(json.contains("\"white\""));
        
        // Verify required fields
        assertTrue(json.contains("\"required\":[\"categories\",\"colors\",\"keywords\",\"price_range\",\"limit\"]"));
    }

    @Test
    void testCartItemsSchema() throws Exception {
        // Create root object
        ObjectType rootObject = new ObjectType(null);
        rootObject.setAdditionalProperties(false);
        
        // Create item object schema
        ObjectType itemObject = new ObjectType(null);
        itemObject.setAdditionalProperties(false);
        
        // Add product_id property
        itemObject.addProperty(
            "product_id", 
            new StringType("ID of the product to add to the cart"),
            true
        );
        
        // Add quantity property
        itemObject.addProperty(
            "quantity",
            new NumericType<>("integer", "Quantity of the product to add to the cart"),
            true
        );
        
        // Create items array with item object
        ArrayType itemsArray = new ArrayType(ObjectType.class, null);
        
        // Add the items array to the root object
        rootObject.addProperty("items", itemsArray, true);
        
        String json = objectMapper.writeValueAsString(rootObject);
        System.out.println(json);
        
        // Verify structure
        assertTrue(json.contains("\"items\":{\"type\":\"array\""));
        assertTrue(json.contains("\"product_id\":{\"type\":\"string\""));
        assertTrue(json.contains("\"quantity\":{\"type\":\"integer\""));
        
        // Verify required fields
        assertTrue(json.contains("\"required\":[\"product_id\",\"quantity\"]"));
        assertTrue(json.contains("\"required\":[\"items\"]"));
    }

    @Test
    void testUserOrdersSchema() throws Exception {
        // Create root object
        ObjectType rootObject = new ObjectType(null);
        rootObject.setAdditionalProperties(false);
        
        // Add user_id property
        rootObject.addProperty(
            "user_id",
            new StringType("The ID of the user to fetch orders for"),
            true
        );
        
        // Add limit property
        rootObject.addProperty(
            "limit",
            new NumericType<Integer>("integer", 
                "The maximum number of orders to return, use 5 by default and increase the number if the relevant order is not found."),
            true
        );
        
        String json = objectMapper.writeValueAsString(rootObject);
        
        System.out.println(json);
        
        // Verify structure
        assertTrue(json.contains("\"user_id\":{\"type\":\"string\""));
        assertTrue(json.contains("\"limit\":{\"type\":\"integer\""));
        
        // Verify descriptions
        assertTrue(json.contains("\"The ID of the user to fetch orders for\""));
        assertTrue(json.contains("\"The maximum number of orders to return, use 5 by default and increase the number if the relevant order is not found.\""));
        
        // Verify required fields
        assertTrue(json.contains("\"required\":[\"user_id\",\"limit\"]"));
        
        // Verify additionalProperties
        assertTrue(json.contains("\"additionalProperties\":false"));
    }

    @Test
    void testOrderReturnSchema() throws Exception {
        // Create root object
        ObjectType rootObject = new ObjectType(null);
        rootObject.setAdditionalProperties(false);
        
        // Add order_id property
        rootObject.addProperty(
            "order_id",
            new StringType("The ID of the order to process a return for"),
            true
        );
        String json = objectMapper.writeValueAsString(rootObject);
        System.out.println(json);
        // Create items array with nested object type
        //ArrayType<ObjectType> itemsArray = new ArrayType<>("The items to return");
        
        // Create item object schema
        ObjectType itemObject = new ObjectType(null);
        itemObject.setAdditionalProperties(false);
        
        // Add product_id property to item object
        itemObject.addProperty(
            "product_id",
            new StringType("The ID of the product to return"),
            true
        );
        
        // Add quantity property to item object
        // itemObject.addProperty(
        //     "quantity",
        //     new NumericType("integer", "The quantity of the product to return"),
        //     true
        // );
        
        // Set the item object as the array items type
        //itemsArray.setItem(itemObject);
        
        // Add the items array to the root object
        rootObject.addProperty("items", itemObject, true);
        
        json = objectMapper.writeValueAsString(rootObject);
        
        System.out.println(json);
        
        // Verify structure
        assertTrue(json.contains("\"order_id\":{\"type\":\"string\""));
        assertTrue(json.contains("\"items\":{\"type\":\"object\""));
        assertTrue(json.contains("\"product_id\":{\"type\":\"string\""));
        
        // Verify required fields
        assertTrue(json.contains("\"required\":[\"order_id\",\"items\"]"));
        
        // Verify additionalProperties
        assertTrue(json.contains("\"additionalProperties\":false"));
    }

    @Test
    void testOrderWithQuantityReturnSchema() throws Exception {
        // Create root object
        ObjectType rootObject = new ObjectType(null);
        rootObject.setAdditionalProperties(false);
        
        // Add order_id property
        rootObject.addProperty(
            "order_id",
            new StringType("The ID of the order to process a return for"),
            true
        );
        String json = objectMapper.writeValueAsString(rootObject);
        System.out.println(json);
        // Create items array with nested object type
        //ArrayType<ObjectType> itemsArray = new ArrayType<>("The items to return");
        
        // Create item object schema
        ObjectType itemObject = new ObjectType(null);
        itemObject.setAdditionalProperties(false);
        
        // Add product_id property to item object
        itemObject.addProperty(
            "product_id",
            new StringType("The ID of the product to return"),
            true
        );
        
        // Add quantity property to item object
        itemObject.addProperty(
            "quantity",
            new NumericType<Integer>("integer", "The quantity of the product to return"),
            true
        );
        
        // Set the item object as the array items type
        //itemsArray.setItem(itemObject);
        
        // Add the items array to the root object
        rootObject.addProperty("items", itemObject, true);
        
        json = objectMapper.writeValueAsString(rootObject);
        
        System.out.println(json);
        
        // Verify structure
        assertTrue(json.contains("\"order_id\":{\"type\":\"string\""));
        assertTrue(json.contains("\"items\":{\"type\":\"object\""));
        assertTrue(json.contains("\"product_id\":{\"type\":\"string\""));
        
        // Verify required fields
        assertTrue(json.contains("\"required\":[\"order_id\",\"items\"]"));
        
        // Verify additionalProperties
        assertTrue(json.contains("\"additionalProperties\":false"));
    }
}