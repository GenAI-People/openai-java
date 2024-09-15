package com.genaipeople.openai.message;

import com.genaipeople.openai.Role;

public class Message {
    private Role role;
    private String content;
    private String name; // optional

    public Message(String content, Role role){
        this.role = role;
        this.content = content;
    }
    
    // Getters
    public Role getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setRole(Role role) {
        this.role = role;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }
}