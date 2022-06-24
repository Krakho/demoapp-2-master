package com.guarino.ingsw.dto;

import java.time.Instant;


public class CommentDTO {
    public Long id;
    public Long postId;
    public Instant createdDate;
    public String text;
    public String userName;
    public CommentDTO() {
    }

    public CommentDTO(Long id, Long postId, Instant createdDate, String text, String userName) {
        this.id = id;
        this.postId = postId;
        this.createdDate = createdDate;
        this.text = text;
        this.userName = userName;
    }
}

