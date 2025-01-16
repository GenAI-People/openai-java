package com.genaipeople.openai.assistant.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ThreadImageUrlMessageContentSerializer.class)
@JsonDeserialize(using = ThreadImageUrlMessageContentDeserializer.class)
public class ThreadImageUrlMessageContent extends ThreadMessageContent {
    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("detail")
    private String detail;

    public ThreadImageUrlMessageContent(String imageUrl) {
        super("image_url");
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
}
