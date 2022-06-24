package com.guarino.ingsw.dto;

public class PostCreationResponse {

    public Long postId;
    public Long subsectionId;
    public String name;
    public Long userId;
    public String url;
    public String description;

    public PostCreationResponse() {
    }

    public PostCreationResponse(Long postId, Long subsectionId, String name, Long userId, String url, String description) {
        this.postId = postId;
        this.subsectionId = subsectionId;
        this.name = name;
        this.userId = userId;
        this.url = url;
        this.description = description;

    }
}
