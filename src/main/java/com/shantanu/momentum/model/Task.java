package com.shantanu.momentum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private Boolean isCompleted;
    private Long createdAt;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private MomentumPOJO user;

}
