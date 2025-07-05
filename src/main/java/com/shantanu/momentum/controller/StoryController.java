package com.shantanu.momentum.controller;

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

    @PostMapping("/post/{taskId}")
    public ResponseEntity<String> postStory(@PathVariable int taskId) {
        return ResponseEntity.ok(storyService.postStory(taskId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Story>> getStories() {
        return ResponseEntity.ok(storyService.getActiveStories());
    }
}

