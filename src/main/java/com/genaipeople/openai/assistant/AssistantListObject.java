package com.genaipeople.openai.assistant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.assistant.response.AssistantObject;

public class AssistantListObject{
    @JsonProperty("object")
    private String object;

    @JsonProperty("data")
    private List<AssistantObject> data;

    @JsonProperty("first_id")
    private String first_id;

    @JsonProperty("last_id")
    private String last_id;

    @JsonProperty("has_more")
    private boolean has_more;

    public AssistantListObject() {}
    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public List<AssistantObject> getData() { return data; }
    public void setData(List<AssistantObject> data) { this.data = data; }

    public String getFirst_id() { return first_id; }
    public void setFirst_id(String first_id) { this.first_id = first_id; }

    public String getLast_id() { return last_id; }
    public void setLast_id(String last_id) { this.last_id = last_id; }

    public boolean isHas_more() { return has_more; }
    public void setHas_more(boolean has_more) { this.has_more = has_more; }
}
