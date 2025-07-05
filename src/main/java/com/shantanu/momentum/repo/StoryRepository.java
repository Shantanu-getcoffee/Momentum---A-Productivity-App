package com.shantanu.momentum.repo;

import com.shantanu.momentum.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findByUser_Username(String username);

    @Query("SELECT s FROM Story s WHERE s.createdAt > :cutoff")
    List<Story> findActiveStories(@Param("cutoff") Long cutoff);
}
