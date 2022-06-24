package com.guarino.ingsw.dto;

public class CommentCreationResponse {

    public Long id;
    public String text;
    public Long userId;

    public CommentCreationResponse() {
    }

    public CommentCreationResponse(Long id, String text, Long userId) {
        this.id = id;
        this.text = text;
        this.userId = userId;
    }
}
