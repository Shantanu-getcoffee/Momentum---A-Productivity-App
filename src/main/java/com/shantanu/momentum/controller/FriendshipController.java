package com.shantanu.momentum.controller;

import com.shantanu.momentum.Enum.FriendshipStatus;
import com.shantanu.momentum.model.Friendship;
import com.shantanu.momentum.model.MomentumPOJO;
import com.shantanu.momentum.repo.FriendshipRepository;
import com.shantanu.momentum.repo.MomentumRepo;
import com.shantanu.momentum.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendshipController {

    @Autowired
    private MomentumRepo userRepo;

    @Autowired
    private FriendshipRepository friendshipRepo;
    @Autowired
    private FriendshipService friendshipService;

    @PostMapping("/add")
    public ResponseEntity<String> sendRequest(@RequestParam String from, @RequestParam String to) {
        String msg = friendshipService.sendFriendRequest(from, to);
        return ResponseEntity.ok(msg);
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptRequest(@RequestParam String from, @RequestParam String to) {
        String msg = friendshipService.acceptFriendRequest(from, to);
        return ResponseEntity.ok(msg);
    }

    public List<Friendship> getPendingRequestsForUser(String username) {
        MomentumPOJO user = userRepo.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return friendshipRepo.findByFriendAndStatus(user, FriendshipStatus.PENDING);
    }

    @GetMapping("/pending/{username}")
    public ResponseEntity<List<Friendship>> getPendingRequests(@PathVariable String username) {
        return ResponseEntity.ok(friendshipService.getPendingRequestsForUser(username));
    }

}
