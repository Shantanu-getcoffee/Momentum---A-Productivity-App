package com.shantanu.momentum.service;

import com.shantanu.momentum.DTO.StoryDTO;
import com.shantanu.momentum.model.Story;
import com.shantanu.momentum.model.Task;
import com.shantanu.momentum.repo.MomentumRepo;
import com.shantanu.momentum.repo.StoryRepository;
import com.shantanu.momentum.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepo;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private MomentumRepo userRepo;

    public StoryDTO createStory(StoryDTO storyDTO) {
        Task task = taskRepo.findById(storyDTO.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!Boolean.TRUE.equals(task.getIsCompleted())) {
            throw new RuntimeException("Only completed tasks can be posted as stories.");
        }

        Story story = new Story();
        story.setCreatedAt(System.currentTimeMillis());
        story.setCaption(storyDTO.getCaption());
        story.setImage(storyDTO.getImage());
        story.setTask(task);
        story.setUser(task.getUser());

        Story saved = storyRepo.save(story);

        StoryDTO responseDTO = new StoryDTO();
        responseDTO.setId(saved.getId());
        responseDTO.setCaption(saved.getCaption());
        responseDTO.setImage(saved.getImage());
        responseDTO.setCreatedAt(saved.getCreatedAt());
        responseDTO.setTaskId(saved.getTask().getId());
        responseDTO.setTitle(saved.getTask().getTitle());
        responseDTO.setDescription(saved.getTask().getDescription());
        responseDTO.setUsername(saved.getUser().getUsername());

        return responseDTO;
    }


    public List<StoryDTO> getActiveStories() {
        long cutoff = System.currentTimeMillis() - (24 * 60 * 60 * 1000);
        List<Story> stories = storyRepo.findActiveStories(cutoff);
        for (Story story : stories) {
            if (story.getImage() != null) {
                System.out.println("Image base64 length for story ID " + story.getId() + ": " + story.getImage().length());
            } else {
                System.out.println("Image is null for story ID " + story.getId());
            }
        }


        return stories.stream().map(story -> new StoryDTO(
                story.getId(),
                story.getCaption(),
                story.getImage(),
                story.getCreatedAt(),
                story.getTask().getId(),
                story.getTask().getTitle(),
                story.getTask().getDescription(),
                story.getUser().getUsername()
        )).collect(Collectors.toList());
    }

}

