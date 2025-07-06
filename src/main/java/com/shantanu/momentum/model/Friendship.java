//package com.shantanu.momentum.model;
//
//import com.shantanu.momentum.Enum.FriendshipStatus;
//import jakarta.persistence.*;
//
//@Entity
//public class Friendship {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // Requester
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private MomentumPOJO user;
//
//    // Person being added
//    @ManyToOne
//    @JoinColumn(name = "friend_id")
//    private MomentumPOJO friend;
//
//    @Enumerated(EnumType.STRING)
//    private FriendshipStatus status; // PENDING, ACCEPTED, REJECTED
//}

