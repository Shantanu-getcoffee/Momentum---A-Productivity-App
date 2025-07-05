package com.shantanu.momentum.service;

import com.shantanu.momentum.model.Story;
import com.shantanu.momentum.model.Task;
import com.shantanu.momentum.repo.MomentumRepo;
import com.shantanu.momentum.repo.StoryRepository;
import com.shantanu.momentum.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepo;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private MomentumRepo userRepo;

    public String postStory(int taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        if (!Boolean.TRUE.equals(task.getIsCompleted())) {
            return "Only completed tasks can be posted as stories.";
        }

        Story story = new Story();
        story.setCreatedAt(System.currentTimeMillis());
        story.setTask(task);
        story.setUser(task.getUser());

        storyRepo.save(story);
        return "Story posted successfully!";
    }

    public List<Story> getActiveStories() {
        long cutoff = System.currentTimeMillis() - (24 * 60 * 60 * 1000); // 24 hours in ms
        return storyRepo.findActiveStories(cutoff);
    }
}

