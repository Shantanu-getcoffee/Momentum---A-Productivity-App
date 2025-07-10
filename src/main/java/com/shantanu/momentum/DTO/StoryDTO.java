package com.shantanu.momentum.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryDTO {
    private Long id;
    private String caption;
    private String image; // Base64-encoded string
    private Long createdAt;
    private int taskId;
    private String title;
    private String description;
    private String username;
}
