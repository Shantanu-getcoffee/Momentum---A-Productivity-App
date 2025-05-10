package com.shantanu.momentum.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private Long createdAt;
    private Boolean isCompleted;
    private String username;
}
