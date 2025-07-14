package com.shantanu.momentum.service;

import com.shantanu.momentum.DTO.PostDTO;
import com.shantanu.momentum.model.MomentumPOJO;
import com.shantanu.momentum.model.Post;
import com.shantanu.momentum.repo.MomentumRepo;
import com.shantanu.momentum.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private MomentumRepo momentumRepo;  // Assuming you have a repository for MomentumPOJO

    public PostDTO createPost(PostDTO postDTO) {
        // Fetch user
        MomentumPOJO user = momentumRepo.findById(postDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Convert DTO to Entity
        Post post = new Post();
        post.setImage(postDTO.getImage());
        post.setCaption(postDTO.getCaption());
        post.setTaskTitle(postDTO.getTaskTitle());
        post.setTaskDescription(postDTO.getTaskDescription());
        post.setTimestamp(postDTO.getTimestamp());
        post.setUser(user);

        // Save entity
        Post savedPost = postRepo.save(post);

        // Convert back to DTO and return
        return new PostDTO(
                savedPost.getId(),
                savedPost.getImage(),
                savedPost.getCaption(),
                savedPost.getTaskTitle(),
                savedPost.getTaskDescription(),
                savedPost.getTimestamp(),
                savedPost.getUser().getUsername()
        );
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepo.findAll();
        return posts.stream().map(post -> new PostDTO(
                post.getId(),
                post.getImage(),
                post.getCaption(),
                post.getTaskTitle(),
                post.getTaskDescription(),
                post.getTimestamp(),
                post.getUser().getUsername()
        )).collect(Collectors.toList());
    }

    public boolean deletePostById(Integer id) {
        Optional<Post> postOptional = postRepo.findById(id);
        if (postOptional.isPresent()) {
            postRepo.deleteById(id);
            return true;
        }
        return false;
    }
}

