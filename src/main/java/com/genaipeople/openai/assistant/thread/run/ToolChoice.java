package com.genaipeople.openai.assistant.thread.run;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.genaipeople.openai.tool.Tool;

@JsonSerialize(using = ToolChoiceSerializer.class)
@JsonDeserialize(using = ToolChoiceDeserializer.class)
public class ToolChoice {
    @JsonProperty("type")
    private ToolChoiceType type;

    @JsonProperty("object")
    private Tool tool;

   public ToolChoiceType getType() { return type; }
   public void setType(ToolChoiceType type) { this.type = type; }

   public Tool getTool() { return tool; }
   public void setTool(Tool tool) { this.tool = tool; }
}
