package com.shantanu.momentum.DTO;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO
{
    private int id;
    private String image;
    private String caption;
    private String taskTitle;
    private String taskDescription;
    private long timestamp;
    private String username;
}

