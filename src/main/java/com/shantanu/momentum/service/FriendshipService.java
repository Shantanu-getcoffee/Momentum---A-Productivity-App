package com.shantanu.momentum.service;

import com.shantanu.momentum.Enum.FriendshipStatus;
import com.shantanu.momentum.model.Friendship;
import com.shantanu.momentum.model.MomentumPOJO;
import com.shantanu.momentum.repo.FriendshipRepository;
import com.shantanu.momentum.repo.MomentumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {

    @Autowired
    private MomentumRepo userRepo;

    @Autowired
    private FriendshipRepository friendshipRepo;

    public String sendFriendRequest(String fromUsername, String toUsername) {
        if (fromUsername.equals(toUsername)) {
            return "You cannot send a friend request to yourself.";
        }

        MomentumPOJO fromUser = userRepo.findById(fromUsername)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        MomentumPOJO toUser = userRepo.findById(toUsername)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Check if friendship already exists
        boolean exists = friendshipRepo.existsByUserAndFriend(fromUser, toUser);
        if (exists) {
            return "Friend request already sent or you're already friends.";
        }

        Friendship friendship = new Friendship();
        friendship.setUser(fromUser);
        friendship.setFriend(toUser);
        friendship.setStatus(FriendshipStatus.PENDING);

        friendshipRepo.save(friendship);

        return "Friend request sent successfully.";
    }
    public String acceptFriendRequest(String fromUsername, String toUsername) {
        MomentumPOJO fromUser = userRepo.findById(fromUsername)
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        MomentumPOJO toUser = userRepo.findById(toUsername)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Find the pending request where fromUser sent request to toUser
        Friendship friendship = friendshipRepo
                .findByUserAndFriendAndStatus(fromUser, toUser, FriendshipStatus.PENDING)
                .orElseThrow(() -> new RuntimeException("Friend request not found or already accepted"));

        friendship.setStatus(FriendshipStatus.ACCEPTED);
        friendshipRepo.save(friendship);

//        // Optional: Create reverse accepted friendship (to → from) for symmetry
//        boolean reverseExists = friendshipRepo.existsByUserAndFriend(toUser, fromUser);
//        if (!reverseExists) {
//            Friendship reverse = new Friendship();
//            reverse.setUser(toUser);
//            reverse.setFriend(fromUser);
//            reverse.setStatus(FriendshipStatus.ACCEPTED);
//            friendshipRepo.save(reverse);
//        }

        return "Friend request accepted.";
    }

    public List<Friendship> getPendingRequestsForUser(String username) {
        MomentumPOJO user = userRepo.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return friendshipRepo.findByFriendAndStatus(user, FriendshipStatus.PENDING);
    }
}

