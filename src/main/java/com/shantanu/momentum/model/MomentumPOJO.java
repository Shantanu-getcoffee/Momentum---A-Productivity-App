package com.shantanu.momentum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MomentumPOJO {
    @Id
    private String username;
    private String email;
    private String password;
    private Integer coins = 0;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Friendship> sentFriendRequests;
//
//    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL)
//    private List<Friendship> receivedFriendRequests;

}
