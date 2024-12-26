package com.genaipeople.openai.tool;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.tool.type.ArrayType;
import com.genaipeople.openai.tool.type.NumericType;
import com.genaipeople.openai.tool.type.ObjectType;
import com.genaipeople.openai.tool.type.PropertyDetails;
import com.genaipeople.openai.tool.type.StringType;

public class FunctionTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testProductRecommendationFunction() throws Exception {
        Function function = new Function("get_product_recommendations", 
            "Searches for products matching certain criteria in the database");
        
        function.addParameter(String.class, "categories", "categories that could be a match", 
            Arrays.asList(
                "coats & jackets", "accessories", "tops", 
                "jeans & trousers", "skirts & dresses", "shoes"
            ), true);
        //function.addParameter(PriceRange.class, "price_range", "price range of the products to search for",
            //null, true);
        function.addParameter(String.class, "colors", "colors that could be a match", 
            Arrays.asList(
                "black", "white", "brown", "red", "blue", "green",
                "orange", "yellow", "pink", "gold", "silver"
            ), false);
        function.addParameter(String.class, "keywords", "keywords that should be present in the item title or description", 
            Arrays.asList(
                "coat", "jacket", "accessory", "top", "jean", "trouser", "skirt", "dress", "shoe"
            ), false);

        String json = objectMapper.writeValueAsString(function);

        System.out.println(json);

        // Verify key elements
        assertTrue(json.contains("\"name\":\"get_product_recommendations\""));
        assertTrue(json.contains("\"description\":\"Searches for products matching certain criteria in the database\""));
        assertTrue(json.contains("\"type\":\"object\""));
        assertTrue(json.contains("\"coats & jackets\""));
        assertTrue(json.contains("\"black\""));
    }

    @Test
    public void testProductRecommendationWithPriceRangeFunction() throws Exception {
        Function function = new Function("get_product_recommendations", 
            "Searches for products matching certain criteria in the database");
        
        function.addParameter(String.class, "categories", "categories that could be a match", 
            Arrays.asList(
                "coats & jackets", "accessories", "tops", 
                "jeans & trousers", "skirts & dresses", "shoes"
            ), true);
        function.addParameter(String.class, "colors", "colors that could be a match", 
            Arrays.asList(
                "black", "white", "brown", "red", "blue", "green",
                "orange", "yellow", "pink", "gold", "silver"
            ), false);
        function.addParameter(String.class, "keywords", "keywords that should be present in the item title or description", 
            Arrays.asList(
                "coat", "jacket", "accessory", "top", "jean", "trouser", "skirt", "dress", "shoe"
            ), false);
        function.addParameter(PriceRange.class, "price_range", "price range of the products to search for",
            null, true);
        
        String json = objectMapper.writeValueAsString(function);

        System.out.println(json);

        // Verify key elements
        assertTrue(json.contains("\"name\":\"get_product_recommendations\""));
        assertTrue(json.contains("\"description\":\"Searches for products matching certain criteria in the database\""));
        assertTrue(json.contains("\"type\":\"object\""));
        assertTrue(json.contains("\"coats & jackets\""));
        assertTrue(json.contains("\"black\""));
        assertTrue(json.contains("\"price_range\""));
    }

    @Test
    public void testProductRecommendationAnonymousClassFunction() throws Exception {
        Function function = new Function("get_product_recommendations", 
            "Searches for products matching certain criteria in the database");

        function.addParameter(new Object() {
            @PropertyDetails(description = "Minimum price")
            public Double min;
            
            @PropertyDetails(description = "Maximum price")
            public int max;
            
            @PropertyDetails(description = "The currencies to search for", 
                enumValues = {"USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "NZD"})
            public List<String> currencies;

            @PropertyDetails(description = "The items to return")
            public List<Item> products;
            
        }.getClass(), "price_range", "price range of the products to search for", null, true);

        // ... rest of the test ...
        String json = objectMapper.writeValueAsString(function);

        System.out.println(json);

        // Verify key elements
        assertTrue(json.contains("\"name\":\"get_product_recommendations\""));
        assertTrue(json.contains("\"description\":\"Searches for products matching certain criteria in the database\""));
        assertTrue(json.contains("\"type\":\"object\""));
        assertTrue(json.contains("\"price_range\""));
    }

    @Test
    public void testProductDetailsFunction() throws Exception {
        Function function = new Function("get_product_details", "Fetches more details about a product");

        function.addParameter(new Object() {
            @PropertyDetails(description = "The ID of the product to fetch details for")
            public Item product;

            @PropertyDetails(description = "The putchase price of the product")
            public Double purchase_price;

            @PropertyDetails(description = "The purchase date of the product")
            public Date purchase_date;

            @PropertyDetails(description = "The purchase currency of the product")
            public String purchase_currency;
        }.getClass(), "product", "The product to fetch details for", null, true);
        String json = objectMapper.writeValueAsString(function);

        System.out.println(json);

        // Verify key elements
        assertTrue(json.contains("\"name\":\"get_product_details\""));
        assertTrue(json.contains("\"description\":\"Fetches more details about a product\""));
        assertTrue(json.contains("\"type\":\"object\""));
        assertTrue(json.contains("\"product\""));
        assertTrue(json.contains("\"purchase_price\""));
        assertTrue(json.contains("\"purchase_date\""));
        assertTrue(json.contains("\"purchase_currency\""));
    }

    @Test
    public void testProcessReturnFunction() throws Exception {
        Function function = new Function();
        function.setName("process_return");
        function.setDescription("Processes a return and creates a return label");
        function.addParameter(String.class, "order_id", "The ID of the order to process a return for", null, true);
        function.addArrayParameter(Item.class, "items", "The items to return", true);


        ObjectType parameters = new ObjectType();
        
        // Add order_id parameter
        StringType orderIdType = new StringType("The ID of the order to process a return for", null);
        parameters.addProperty("order_id", orderIdType, true);

        // Create items array with nested object type
        ObjectType itemType = new ObjectType();
        itemType.addProperty("product_id", new StringType("The ID of the product to return", null), true);
        itemType.addProperty("quantity", new NumericType<Integer>("integer", "The quantity of the product to return"), true);
        
        ArrayType itemsType = new ArrayType(itemType.getClass(), "The items to return");
        parameters.addProperty("items", itemsType, true);

        function.setParameters(parameters);

        // Serialize and verify
        String json = objectMapper.writeValueAsString(function);
        System.out.println(json);
        // Verify key elements
        assertTrue(json.contains("\"name\":\"process_return\""));
        assertTrue(json.contains("\"description\":\"Processes a return and creates a return label\""));
        assertTrue(json.contains("\"required\":[\"order_id\",\"items\"]"));
        assertTrue(json.contains("\"required\":[\""));
        assertTrue(json.contains("\"additionalProperties\":false"));
    }

    @Test
    public void testProcessWithBeansReturnFunction() throws Exception {
        Function function = new Function();
        function.setName("process_return");
        function.setDescription("Processes a return and creates a return label");
        function.addParameter(String.class, "order_id", "The ID of the order to process a return for", null, true);
        function.addArrayParameter(Item.class, "products", "The items to return", true);

        // Serialize and verify
        String json = objectMapper.writeValueAsString(function);
        System.out.println(json);
        // Verify key elements
        assertTrue(json.contains("\"name\":\"process_return\""));
        assertTrue(json.contains("\"description\":\"Processes a return and creates a return label\""));
        assertTrue(json.contains("\"required\":[\"order_id\",\"products\"]"));
        assertTrue(json.contains("\"required\":[\"product_id\",\"quantity\"]"));
        assertTrue(json.contains("\"additionalProperties\":false"));
    }

    @Test
    public void tesPendingOrders() throws Exception {
        Function function = new Function();
        function.setName("get_pending_orders");
        function.setDescription("Fetches all pending orders");
        function.addParameter(Date.class, "date", "The date for which to list pending orders, formatted as YYYY-MM-DD", null, true);
        function.addParameter(String.class, "status", "Filter to specify the status of the orders to list, default is 'pending'", 
            Arrays.asList("pending", "completed", "canceled"), true);

        String json = objectMapper.writeValueAsString(function);
        System.out.println(json);
        // Verify key elements
        assertTrue(json.contains("\"name\":\"get_pending_orders\""));
        assertTrue(json.contains("\"description\":\"Fetches all pending orders\""));
        assertTrue(json.contains("\"required\":[\"date\",\"status\"]"));
        assertTrue(json.contains("\"additionalProperties\":false"));
    }

    @Test
    public void tesPendingOrdersAsAnonObject() throws Exception {
        Function function = new Function();
        function.setName("get_pending_orders");
        function.setDescription("Fetches all pending orders");
        function.addParameter(new Object(){
            @PropertyDetails(description = "The date for which to list pending orders, formatted as YYYY-MM-DD")
            public Date date;

            @PropertyDetails(description = "Filter to specify the status of the orders to list, default is 'pending'", 
                enumValues = {"pending", "completed", "canceled"})
            public String status;
        }.getClass(), "date", "The date for which to list pending orders, formatted as YYYY-MM-DD", null, true);

        String json = objectMapper.writeValueAsString(function);
        System.out.println(json);
        // Verify key elements
        assertTrue(json.contains("\"name\":\"get_pending_orders\""));
        assertTrue(json.contains("\"description\":\"Fetches all pending orders\""));
        assertTrue(json.contains("\"required\":[\"date\",\"status\"]"));
        assertTrue(json.contains("\"date\""));
        assertTrue(json.contains("\"status\""));
        assertTrue(json.contains("\"enum\""));
        assertTrue(json.contains("\"pending\""));
        assertTrue(json.contains("\"completed\""));
        assertTrue(json.contains("\"canceled\""));
        assertTrue(json.contains("\"additionalProperties\":false"));
    }
} 

class PriceRange {
    private Double min;
    private Double max;
    @PropertyDetails(description = "The currencies to search for", 
        enumValues = {"USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "NZD", "SEK", "NOK", "DKK", "PLN", "CZK", "HUF", "RON", "BGN", "HRK", "RUB", "TRY", "ZAR", "BRL", "MXN", "INR", "IDR", "THB", "MYR", "PHP", "SGD", "HKD", "KRW", "TWD", "MYR", "PHP", "SGD", "HKD", "KRW", "TWD"})
    private List<String> currencies;

    @PropertyDetails(description = "The items to return")
    private List<Item> products;

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public List<Item> getProducts() {
        return products;
    }
}

class Item {
    private String product_id;
    private Integer quantity;

    public String getProduct_id() {
        return product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }
}