package com.shantanu.momentum.service;

import com.shantanu.momentum.DTO.PostDTO;
import com.shantanu.momentum.DTO.TaskDTO;
import com.shantanu.momentum.model.MomentumPOJO;
import com.shantanu.momentum.model.Task;
import com.shantanu.momentum.repo.MomentumRepo;
import com.shantanu.momentum.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    MomentumRepo momentumRepo;

    @Autowired
    private TaskRepo taskRepo;


    public void deleteTask(int id) {
        taskRepo.deleteById(id);
    }

    public TaskDTO updateTask(Integer id, Task task) {
        // Find the task by ID
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (optionalTask.isEmpty()) {
            return null; // Task not found
        }

        Task existingTask = optionalTask.get();

        // Update the task fields
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setCreatedAt(task.getCreatedAt());
        existingTask.setIsCompleted(task.getIsCompleted());

        // Check if username is provided and update user
        if (task.getUser() != null && task.getUser().getUsername() != null) {
            String username = task.getUser().getUsername();
            Optional<MomentumPOJO> optionalUser = momentumRepo.findById(username);
            optionalUser.ifPresent(existingTask::setUser);
        }

        // Save the updated task
        taskRepo.save(existingTask);

        // Convert to DTO and return
        return new TaskDTO(
                existingTask.getId(),
                existingTask.getTitle(),
                existingTask.getDescription(),
                existingTask.getCreatedAt(),
                existingTask.getIsCompleted(),
                existingTask.getUser().getUsername()
        );
    }



    public List<TaskDTO> getAllTasks() {
        return taskRepo.findAll().stream().map(task -> new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getIsCompleted(),
                task.getUser().getUsername()
        )).collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO dto) {
        // Fetch the user (MomentumPOJO) using the username
        MomentumPOJO user = momentumRepo.findById(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create and populate Task entity
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setIsCompleted(dto.getIsCompleted());
        task.setCreatedAt(dto.getCreatedAt());
        task.setUser(user); // Associate user

        // Save the task and retrieve the saved instance (with generated ID)
        Task savedTask = taskRepo.save(task);

        // Return a TaskDTO with the generated task ID and other details
        return new TaskDTO(
                savedTask.getId(),                      // Include the ID here
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getCreatedAt(),
                savedTask.getIsCompleted(),
                savedTask.getUser().getUsername()
        );
    }

    public String completeTaskAndReward(int taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getIsCompleted()) {
            task.setIsCompleted(true);
            MomentumPOJO user = task.getUser();
            user.setCoins(user.getCoins() + 10); // e.g., 10 coins per task
            taskRepo.save(task);
            momentumRepo.save(user);
            return "Task completed! 10 coins awarded.";
        } else {
            return "Task was already completed.";
        }
    }

}


