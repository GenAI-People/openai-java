package com.genaipeople.openai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.genaipeople.openai.message.Message;
import com.genaipeople.openai.response.Choice;
import com.genaipeople.openai.response.ToolCall;
import com.genaipeople.openai.text.ChatRequest;
import com.genaipeople.openai.text.ChatResponse;
import com.genaipeople.openai.text.ToolChoice;
import com.genaipeople.openai.tool.Function;
import com.genaipeople.openai.tool.type.PropertyDetails;

public class FunctionChatTest {
    private Chat chat;
    private static final String API_KEY = "API_KEY";
    private static final String MODEL = "gpt-4o-mini";

    @BeforeEach
    void setUp() {
        chat = new Chat(API_KEY);
    }

    @Test
    public void testFunctionCallToCheckWeather() {
        List<Message> messages = Arrays.asList(new Message("What'\\''s the weather like in Boston today?", Role.user));
        ChatRequest request = new ChatRequest(messages, MODEL);
        Function function = new Function("get_current_weather", "Get the current weather in a given location");
        function.addParameter(String.class, "location", "The city and state, e.g. San Francisco, CA", null, true);
        function.addParameter(String.class, "unit", "The unit of temperature to use, either 'celsius' or 'fahrenheit'", 
            Arrays.asList("celsius", "fahrenheit"), false);

        request.setTools(Arrays.asList(function));
        request.setToolChoice(ToolChoice.auto.toString());
        try {
            CompletableFuture<ChatResponse> futureResponse = chat.complete(request);
            ChatResponse actualResponse = futureResponse.get();
            assertNotNull(actualResponse);
            for(Choice choice : actualResponse.getChoices()){
                for(ToolCall toolCall : choice.getMessage().getToolCalls()){
                    com.genaipeople.openai.response.Function responseFunction = 
                        (com.genaipeople.openai.response.Function) toolCall.getFunction();
                    assertTrue(responseFunction.getName().equals("get_current_weather"));
                    assertNotNull(responseFunction.getParameters());
                    assertTrue(responseFunction.getParameters().containsKey("location"));
                }
            }
        } catch (InterruptedException e) {
            assertTrue(false);
        } catch (ExecutionException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testProductRecommendationsFunction() throws Exception {
        List<Message> messages = Arrays.asList(new Message("List out all the red shoes that are within 5$ to 10$?", Role.user));
        ChatRequest request = new ChatRequest(messages, MODEL);
        request.setTools(Arrays.asList(getProductRecommendationsFunction()));
        request.setToolChoice(ToolChoice.auto.toString());
        try {
            CompletableFuture<ChatResponse> futureResponse = chat.complete(request);
            ChatResponse actualResponse = futureResponse.get();
            for(Choice choice : actualResponse.getChoices()){
                assertEquals(choice.getFinishReason(), FinishReason.tool_calls.toString());
                for(ToolCall toolCall : choice.getMessage().getToolCalls()){
                    com.genaipeople.openai.response.Function responseFunction = 
                        (com.genaipeople.openai.response.Function) toolCall.getFunction();
                    assertTrue(responseFunction.getName().equals("get_product_recommendations"));
                    assertTrue(responseFunction.getParameters().containsKey("price_range"));
                    @SuppressWarnings("unchecked")
                    Map<String, Object> priceRange = (Map<String, Object>) responseFunction.getParameters().get("price_range");
                    assertTrue(priceRange.containsKey("min"));
                    assertTrue(priceRange.containsKey("max"));
                }
            }
        } catch (InterruptedException e) {
            assertTrue(false);
        } catch (ExecutionException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testProductRecommendationsOrDetailsFunction() throws Exception {
        List<Message> messages = Arrays.asList(new Message("Please provide me the details of the Macbook Pro that i purchased. The product id is 1234", Role.user));
        ChatRequest request = new ChatRequest(messages, MODEL);
        request.setTools(Arrays.asList(getProductRecommendationsFunction(), getProductDetailsFunction()));
        request.setToolChoice(ToolChoice.auto.toString());
        try {
            CompletableFuture<ChatResponse> futureResponse = chat.complete(request);
            ChatResponse actualResponse = futureResponse.get();
            for(Choice choice : actualResponse.getChoices()){
                assertEquals(choice.getFinishReason(), FinishReason.tool_calls.toString());
                for(ToolCall toolCall : choice.getMessage().getToolCalls()){
                    com.genaipeople.openai.response.Function responseFunction = 
                        (com.genaipeople.openai.response.Function) toolCall.getFunction();
                    assertTrue(responseFunction.getName().equals("get_product_details"));
                    assertTrue(responseFunction.getParameters().containsKey("product_id"));
                    assertTrue(responseFunction.getParameters().get("product_id").equals("1234"));
                }
            }
        } catch (InterruptedException e) {
            assertTrue(false);
        } catch (ExecutionException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testProductRecommendationsForPriceRangeFunction() throws Exception {
        List<Message> messages = Arrays.asList(new Message("Recommend a pair of red shoes that are within the price range of minimum 5$ to maximum 10$. Show me the details of the product and present me with 5 different options", Role.user));
        ChatRequest request = new ChatRequest(messages, MODEL);
        request.setTools(Arrays.asList(getProductRecommendationsFunction(), getProductDetailsFunction()));
        request.setToolChoice(ToolChoice.auto.toString());
        try {
            CompletableFuture<ChatResponse> futureResponse = chat.complete(request);
            ChatResponse actualResponse = futureResponse.get();
            for(Choice choice : actualResponse.getChoices()){
                assertEquals(choice.getFinishReason(), FinishReason.tool_calls.toString());
                for(ToolCall toolCall : choice.getMessage().getToolCalls()){
                    com.genaipeople.openai.response.Function responseFunction = 
                        (com.genaipeople.openai.response.Function) toolCall.getFunction();
                    assertTrue(responseFunction.getName().equals("get_product_recommendations"));
                    
                    Map<String, Object> parameters = responseFunction.getParameters();
                    assertNotNull(parameters);
                    assertTrue(parameters.containsKey("limit"));   

                    @SuppressWarnings("unchecked")
                    Map<String, Object> priceRange = parameters.containsKey("price_range") ? 
                        (Map<String, Object>) parameters.get("price_range") : null;
                    assertNotNull(priceRange);
                    assertTrue(priceRange.containsKey("min"));
                    assertTrue(priceRange.containsKey("max"));
                }
            }
        } catch (InterruptedException e) {
            assertTrue(false);
        } catch (ExecutionException e) {
            assertTrue(false);
        }
    }

    private Function getProductRecommendationsFunction(){
        Function function = new Function("get_product_recommendations", 
        "Searches for products matching certain criteria in the database");
    
        // Add categories array parameter
        function.addParameter(new Object() {
            @PropertyDetails(description = "categories that could be a match",
                enumValues = {"coats & jackets", "accessories", "tops", 
                            "jeans & trousers", "skirts & dresses", "footwear"})
            public List<String> categories;
            
            @PropertyDetails(description = "The color of the product to be recommended",
                enumValues = {"black", "white", "brown", "red", "blue", "green",
                            "orange", "yellow", "pink", "gold", "silver"})
            public List<String> colors;
            
            @PropertyDetails(description = "keywords that should be present in the item title or description")
            public List<String> keywords;

            @PropertyDetails(description = "price range for the products to be recommended")
            public PriceRange price_range;
            
            @PropertyDetails(description = "The maximum number of products to return, use 5 by default if nothing is specified by the user")
            public Integer limit;
            }.getClass(), "parameters", null, null, true);
        return function;
    }
    
    private Function getProductDetailsFunction(){
        Function function = new Function("get_product_details", 
            "Fetches more details about a product");
        
        function.addParameter(String.class, "product_id", 
            "The ID of the product to fetch details for", null, true);
        return function;
    }
}
class PriceRange {
    @PropertyDetails(description = "The minimum price to search for")
    private Double min;
    @PropertyDetails(description = "The maximum price to search for")
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