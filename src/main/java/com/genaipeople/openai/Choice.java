package com.genaipeople.openai;

import com.genaipeople.openai.message.Message;

public class Choice {
    Integer index;
    Message message;
    String finish_reason;

    // Getters
    public Integer getIndex() {
        return index;
    }

    public Message getMessage() {
        return message;
    }

    public String getFinishReason() {
        return finish_reason;
    }

    // Setters
    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setFinishReason(String finish_reason) {
        this.finish_reason = finish_reason;
    }
}