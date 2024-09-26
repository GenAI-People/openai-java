package com.genaipeople.openai.message.content;


import com.genaipeople.openai.message.Content;

public class ImageContent implements Content {
    private final String content;
    private final String imageUrl;

    public ImageContent(String content, String imageUrl) {
        this.content = content;
        this.imageUrl = imageUrl;
    }

    @Override
    public String getType() {
        return "image_url";
    }

    @Override
    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
