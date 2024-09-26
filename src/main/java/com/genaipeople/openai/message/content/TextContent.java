package com.genaipeople.openai.message.content;

import com.genaipeople.openai.message.Content;

public class TextContent implements Content {
    private final String content;

    public TextContent(String content) {
        this.content = content;
    }

    @Override
    public String getType() {
        return "text";
    }

    @Override
    public String getContent() {
        return content;
    }
}
