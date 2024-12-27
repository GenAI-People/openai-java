package com.genaipeople.openai.tool.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchemaGenerator {
    
    public static ObjectType generateSchema(Class<?> clazz) {
        ObjectType schema = new ObjectType();
        
        for (Field field : clazz.getDeclaredFields()) {
            // Skip synthetic fields (like this$0 in anonymous classes)
            if (field.isSynthetic()) {
                continue;
            }
            
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            String propertyName = (jsonProperty != null) ? jsonProperty.value() : field.getName();
            
            Type propertyType = generatePropertySchema(field);
            if (propertyType != null) {
                schema.addProperty(propertyName, propertyType, true);
            }
        }
        
        return schema;
    }
    
    private static Type generatePropertySchema(Object fieldInfo) {
        Class<?> type;
        String description;
        List<?> enumValues = null;

        if (fieldInfo instanceof Field) {
            Field field = (Field) fieldInfo;
            type = field.getType();
            PropertyDetails propertyDetails = field.getAnnotation(PropertyDetails.class);
            if(propertyDetails != null){
                description = propertyDetails.description() != null ? propertyDetails.description() : getDescription(field);
                if(propertyDetails.enumValues() != null && propertyDetails.enumValues().length > 0){
                    enumValues = Arrays.asList(propertyDetails.enumValues());
                }else{
                    enumValues = Arrays.asList();
                }
            } else {
                description = getDescription(field);
            }
        } else {
            TypeField typeField = (TypeField) fieldInfo;
            type = typeField.getType();
            description = typeField.getName();
        }
        
        // Handle String
        if (type == String.class || type == Date.class || type == Boolean.class) {
            StringType stringType = new StringType(description);
            if (enumValues != null) {
                stringType.setEnums(enumValues);
            }else{
                stringType.setEnums(Arrays.asList());
            }
            return stringType;
        }
        
        // Handle Numbers (Integer, Long, Double, etc.)
        if (Number.class.isAssignableFrom(type) || isNumericPrimitive(type)) {
            String numericType = isIntegerType(type) ? "integer" : "number";
            if(numericType.equals("integer")){
                NumericType<Integer> numericTypeValue = new NumericType<Integer>(numericType, description);
                if(enumValues != null){
                    numericTypeValue.setEnums(enumValues);
                }else{
                    numericTypeValue.setEnums(Arrays.asList());
                }
                return numericTypeValue;
            } else {
                NumericType<Double> numericTypeValue = new NumericType<Double>(numericType, description);
                if(enumValues != null){
                    numericTypeValue.setEnums(enumValues);
                }else{
                    numericTypeValue.setEnums(Arrays.asList());
                }
                return numericTypeValue;
            }
        }
        
        // Handle Arrays/Lists
        if (Collection.class.isAssignableFrom(type)) {
            Class<?> itemType = getGenericType(fieldInfo);
            if (itemType != null) {
                TypeField itemSchema = getFieldForType(itemType);
                return new ArrayType(itemSchema.getType(), description);
            }
        }
        ObjectType objectType = generateSchema(type);
        objectType.setDescription(description);
        return objectType;
    }
    
    private static boolean isNumericPrimitive(Class<?> type) {
        return type == int.class || type == long.class || 
               type == double.class || type == float.class || 
               type == short.class || type == byte.class;
    }
    
    private static boolean isIntegerType(Class<?> type) {
        return type == Integer.class || type == Long.class || 
               type == Short.class || type == Byte.class ||
               type == int.class || type == long.class || 
               type == short.class || type == byte.class;
    }
    
    private static Class<?> getGenericType(Object fieldInfo) {
        if (fieldInfo instanceof Field) {
            Field field = (Field) fieldInfo;
            if (field.getGenericType() instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) field.getGenericType();
                return (Class<?>) paramType.getActualTypeArguments()[0];
            }
        }
        return null;
    }
    
    private static class TypeField {
        private final Class<?> type;
        private final String name;
        
        public TypeField(Class<?> type) {
            this.type = type;
            this.name = type.getSimpleName().toLowerCase();
        }
        
        public Class<?> getType() {
            return type;
        }
        
        public String getName() {
            return name;
        }
    }
    
    private static TypeField getFieldForType(Class<?> type) {
        return new TypeField(type);
    }
    
    private static String getDescription(Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        if (jsonProperty != null && !jsonProperty.value().isEmpty()) {
            return jsonProperty.value();
        }
        return field.getName();
    }
} 