package com.guarino.ingsw.controller;

import com.guarino.ingsw.dto.CommentCreationResponse;
import com.guarino.ingsw.dto.CommentDTO;
import com.guarino.ingsw.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentsController {
    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentCreationResponse> createComment(@RequestBody CommentDTO commentDto) {
        CommentCreationResponse response = commentService.save(commentDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getAllCommentsForPost(postId);
        return ResponseEntity.status(OK).body(comments);
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForUser(@PathVariable String userName) {
        return ResponseEntity.status(OK).body(commentService.getAllCommentsForUser(userName));
    }

}
