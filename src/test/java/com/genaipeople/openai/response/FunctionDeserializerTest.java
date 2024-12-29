package com.genaipeople.openai.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class FunctionDeserializerTest {

    @Test
    public void testFunctionDeserialization() throws Exception {
        String json = "{" +
            "\"name\": \"appointmentDetails\"," +
            "\"arguments\": {" +
            "\"doctorName\": \"John\"," +
            "\"appointmentDate\": \"2023-11-19T09:00:00\"" +
            "}}";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Function.class, new FunctionDeserializer());
        mapper.registerModule(module);

        Function function = mapper.readValue(json, Function.class);
        assertEquals("appointmentDetails", function.getName());
        assertNotNull(function.getParameters());
        
        // Verify nested object structure
        assertNotNull(function.getParameters().get("doctorName"));
        assertNotNull(function.getParameters().get("appointmentDate"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNestedSingleObjectDeserialization() throws Exception {
        String json = "{"
            + "\"name\": \"orderDetails\","
            + "\"arguments\": {"
            + "  \"customer\": {"
            + "    \"name\": \"John Doe\""
            + "  }"
            + "}}";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Function.class, new FunctionDeserializer());
        mapper.registerModule(module);

        Function function = mapper.readValue(json, Function.class);
        assertEquals("orderDetails", function.getName());
        // Verify top level structure
        assertNotNull(function.getParameters());
        assertNotNull(function.getParameters().get("customer"));
        
        // Verify customer object structure
        Map<String, Object> customer = (Map<String, Object>) function.getParameters().get("customer");
        assertNotNull(customer);
        assertNotNull(customer.get("name"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNestedMultipleObjectDeserialization() throws Exception {
        String json = "{"
            + "\"name\": \"orderDetails\","
            + "\"arguments\": {"
            + "  \"customer\": {"
            + "    \"name\": \"John Doe\","
            + "    \"contact\": {"
            + "      \"email\": \"john@example.com\","
            + "      \"phone\": \"123-456-7890\""
            + "    }"
            + "  },"
            + "  \"shipping\": {"
            + "    \"address\": {"
            + "      \"street\": \"123 Main St\","
            + "      \"city\": \"Springfield\","
            + "      \"zipCode\": \"12345\""
            + "    },"
            + "    \"method\": \"express\""
            + "  }"
            + "}}";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Function.class, new FunctionDeserializer());
        mapper.registerModule(module);

        Function function = mapper.readValue(json, Function.class);
        assertEquals("orderDetails", function.getName());
        // Verify top level structure
        assertNotNull(function.getParameters());
        Map<String, Object> customer = (Map<String, Object>) function.getParameters().get("customer");
        assertNotNull(customer);
        assertNotNull(customer.get("name"));
        Map<String, Object> contact = (Map<String, Object>) customer.get("contact");
        assertNotNull(contact);
        assertNotNull(contact.get("email"));
        assertNotNull(contact.get("phone"));
        
        Map<String, Object> shipping = (Map<String, Object>) function.getParameters().get("shipping");
        assertNotNull(shipping);
        assertNotNull(shipping.get("method"));
        Map<String, Object> address = (Map<String, Object>) shipping.get("address");
        assertNotNull(address);
        assertNotNull(address.get("street"));
        assertNotNull(address.get("city"));
        assertNotNull(address.get("zipCode"));
    }

    @Test
    public void testFunctionDeserializationWithArguments() throws Exception {
        String json = "{\"name\":\"get_current_weather\",\"arguments\":{\"location\":\"Boston, MA\"}}";
        ObjectMapper mapper = new ObjectMapper();
        Function function = mapper.readValue(json, Function.class);
        assertEquals("get_current_weather", function.getName());
        assertNotNull(function.getParameters());   
        assertNotNull(function.getParameters().get("location"));
    }
} 