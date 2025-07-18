package com.shantanu.momentum.repo;

import com.shantanu.momentum.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    // You can define custom queries if needed
}

