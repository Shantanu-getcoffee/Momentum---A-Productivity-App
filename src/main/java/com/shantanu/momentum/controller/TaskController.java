package com.shantanu.momentum.controller;

import com.shantanu.momentum.DTO.TaskDTO;
import com.shantanu.momentum.model.Task;
import com.shantanu.momentum.repo.TaskRepo;
import com.shantanu.momentum.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO dto) {
        TaskDTO savedTask = taskService.createTask(dto); // Now returns the saved Task
        return ResponseEntity.ok(savedTask); // Returns full task with generated ID
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer id, @RequestBody Task task) {
        TaskDTO updatedTask = taskService.updateTask(id, task);
        if (updatedTask == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTask);
    }

    @PostMapping("/task/{id}/complete")
    public ResponseEntity<String> completeTask(@PathVariable int id) {
        String msg = taskService.completeTaskAndReward(id);
        return ResponseEntity.ok(msg);
    }


}