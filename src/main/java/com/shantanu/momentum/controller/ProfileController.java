package com.shantanu.momentum.controller;

import com.shantanu.momentum.service.MomentumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ProfileController {
    @Autowired
    private MomentumService momentumService;

    @GetMapping("/{username}/profile-picture")
    public ResponseEntity<String> getProfilePicture(@PathVariable String username) {
        String imageBase64 = momentumService.getProfilePicture(username);
        return ResponseEntity.ok(imageBase64);
    }
}
