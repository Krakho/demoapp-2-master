package com.guarino.ingsw.controller;

import com.guarino.ingsw.dto.PostCreationResponse;
import com.guarino.ingsw.dto.PostDTO;
import com.guarino.ingsw.dto.PostRequest;
import com.guarino.ingsw.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostCreationResponse> createPost(@RequestBody PostRequest postRequest) {
        PostCreationResponse response = postService.create(postRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-subsection/{id}")
    public ResponseEntity<List<PostDTO>> getPostsBySubsection(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostsBySubsection(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostDTO>> getPostsByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }
}