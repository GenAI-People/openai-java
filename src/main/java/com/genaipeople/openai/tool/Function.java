package com.genaipeople.openai.tool;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.genaipeople.openai.tool.type.ArrayType;
import com.genaipeople.openai.tool.type.NumericType;
import com.genaipeople.openai.tool.type.ObjectType;
import com.genaipeople.openai.tool.type.SchemaGenerator;
import com.genaipeople.openai.tool.type.StringType;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = FunctionSerializer.class)
public class Function extends Tool{
    @JsonProperty("description")
    private String description = "";

    @JsonProperty("name")
    private String name = "";

    @JsonProperty("parameters")
    private ObjectType parameters;

    @JsonProperty("strict")
    private Boolean strict;

    public Function() {
        this.name = null;
        this.description = null;
        this.parameters = null;
        this.strict = null;
    }

    public Function(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.matches("^[a-zA-Z0-9_-]{1,64}$")) {
            throw new IllegalArgumentException("Name must only contain a-z, A-Z, 0-9, underscores and dashes, with max length of 64");
        }
        this.name = name;
    }

    public ObjectType getParameters() {
        return parameters;
    }

    public void setParameters(ObjectType parameters) {
        this.parameters = parameters;
    }

    public Boolean getStrict() {
        return strict;
    }

    public void setStrict(Boolean strict) {
        this.strict = strict;
    }

    public String getType(){
        return "function";
    }

    public void addArrayParameter(Class<?> type, String name, String description, boolean required) {
        ArrayType arrayType = new ArrayType(type, description);
        this.parameters.addProperty(name, arrayType, required);
    }

    public <T> void addParameter(Class<T> type, String name, String description, 
        List<T> enumValues, boolean required) {
        if(this.parameters == null){
            this.parameters = new ObjectType();
        }
        if (String.class.equals(type) || Date.class.equals(type) || java.sql.Date.class.equals(type)) {
            StringType stringType = new StringType(description);
            if(enumValues != null && !enumValues.isEmpty()){
                stringType.setEnums(enumValues);
            }else{
                stringType.setEnums(Arrays.asList());
            }
            this.parameters.addProperty(name, stringType, required);
            return;
        }
        if (Number.class.isAssignableFrom(type)) {
            boolean isInteger = isIntegerType(type);
            if(isInteger){
                NumericType<Integer> numericType = new NumericType<Integer>("integer", description);
                if(enumValues != null && !enumValues.isEmpty()){
                    numericType.setEnums(enumValues);
                }else{
                    numericType.setEnums(Arrays.asList());
                }
                this.parameters.addProperty(name, numericType, required);
            } else {
                NumericType<Double> numericType = new NumericType<Double>("number", description);
                this.parameters.addProperty(name, numericType, required);
                if(enumValues != null && !enumValues.isEmpty()){
                    numericType.setEnums(enumValues);
                }else{
                    numericType.setEnums(Arrays.asList());
                }
            }
            return;
        }

        ObjectType objectType = SchemaGenerator.generateSchema(type);
        this.parameters.addProperty(name, objectType, required);
    }

    private boolean isIntegerType(Class<?> type) {
        return type == Integer.class || type == Long.class || 
               type == Short.class || type == Byte.class ||
               type == int.class || type == long.class || 
               type == short.class || type == byte.class;
    }

}
