package com.genaipeople.openai;

import java.util.regex.Pattern;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.text.ErrorResponse;

public class OpenAICommons {
    public static final Pattern ERROR_PATTERN = Pattern.compile("^\\s*\\{\\s*\"error\":\\s*\\{");

    public static <T> T stringToType(String responseString, Class<T> valueClass, ObjectMapper mapper) throws Exception {
        handleError(responseString, mapper);
        return mapper.readValue(responseString, valueClass);
    }

    public static <T> T stringToType(String responseString, TypeReference<T> valueTypeRef, ObjectMapper mapper) throws Exception {
        handleError(responseString, mapper);
        return mapper.readValue(responseString, valueTypeRef);
    }

    public static void handleError(String responseString, ObjectMapper mapper) throws Exception {
        if (OpenAICommons.ERROR_PATTERN.matcher(responseString).find()) {
            String jsonPart = responseString.substring(responseString.indexOf("{"));
            ErrorResponse errorResponse = mapper.readValue(jsonPart, ErrorResponse.class);
            throw new RuntimeException("Code: " + errorResponse.getError().getCode() + 
                " Message: " + errorResponse.getError().getMessage());
        }
    }
}
