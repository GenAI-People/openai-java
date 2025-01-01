package com.genaipeople.openai.assistant.thread;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.genaipeople.openai.assistant.ToolResource;
import com.genaipeople.openai.message.Message;

public class ThreadRequest {
    /*
     * messagesarray
     * Optional A list of messages to start the thread with.
     */
    @JsonProperty("messages")
    private List<Message> messages;

    /*
     * tool_resources
     * object or null
     * Optional
     * A set of resources that are made available to the assistant's tools in this
     * thread. The resources are specific to the type of tool. For example, the
     * code_interpreter tool requires a list of file IDs, while the file_search tool
     * requires a list of vector store IDs.
     */
    @JsonProperty("tool_resources")
    private ToolResource toolResources;

    /*
     * metadata
     * object or null
     * Optional
     * Set of 16 key-value pairs that can be attached to an object. 
     * This can be useful for storing additional information about the object in a structured format. 
     * Keys can be a maximum of 64 characters long and values can be a maximum of 512 characters long.
     */
    @JsonProperty("metadata")
    private Map<String, String> metadata;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public ToolResource getToolResources() {
        return toolResources;
    }

    public void setToolResources(ToolResource toolResources) {
        this.toolResources = toolResources;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
