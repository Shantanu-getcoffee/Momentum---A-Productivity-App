package com.shantanu.momentum.controller;

import com.shantanu.momentum.DTO.PostDTO;
import com.shantanu.momentum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        boolean deleted = postService.deletePostById(id);
        if (deleted) {
            return ResponseEntity.ok("Post deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Post not found.");
        }
    }
}

