package com.genaipeople.openai.assistant.thread.run;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RunStepList {
    @JsonProperty("object")
    private String object;

    @JsonProperty("data")
    private List<RunStepObject> data;

    @JsonProperty("first_id")
    private String firstId;

    @JsonProperty("last_id")
    private String lastId;

    @JsonProperty("has_more")
    private Boolean hasMore;

    public String getObject() {
        return object;
    }   

    public List<RunStepObject> getData() {
        return data;
    }

    public String getFirstId() {
        return firstId;
    }

    public String getLastId() {
        return lastId;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    @Override
    public String toString() {
        return "RunStepList [object=" + object + ", data=" + data + ", firstId=" + firstId + ", lastId=" + lastId
                + ", hasMore=" + hasMore + "]";
    }
}
