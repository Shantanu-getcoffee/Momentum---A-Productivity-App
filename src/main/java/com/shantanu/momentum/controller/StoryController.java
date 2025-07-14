package com.shantanu.momentum.controller;

import com.shantanu.momentum.DTO.StoryDTO;
import com.shantanu.momentum.model.Story;
import com.shantanu.momentum.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @PostMapping("/post")
    public ResponseEntity<StoryDTO> postStory(@RequestBody StoryDTO storyDTO) {
        StoryDTO createdStory = storyService.createStory(storyDTO);
        return ResponseEntity.ok(createdStory);
    }

    @GetMapping("/active")
    public ResponseEntity<List<StoryDTO>> getStories() {
        return ResponseEntity.ok(storyService.getActiveStories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStory(@PathVariable Long id) {
        boolean deleted = storyService.deleteStoryById(id);
        if (deleted) {
            return ResponseEntity.ok("Story deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Story not found.");
        }
    }
}

