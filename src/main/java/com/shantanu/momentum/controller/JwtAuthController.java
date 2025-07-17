package com.shantanu.momentum.controller;

import com.shantanu.momentum.DTO.CoinsDTO;
import com.shantanu.momentum.model.MomentumPOJO;
//import com.shantanu.momentum.service.JwtService;
import com.shantanu.momentum.repo.MomentumRepo;
import com.shantanu.momentum.service.JwtService;
import com.shantanu.momentum.service.MomentumService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class JwtAuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MomentumRepo momentumRepo;

    @Autowired
    private MomentumService momentumService;

    @PostMapping("/save")
    public ResponseEntity<String> auth(@RequestBody MomentumPOJO user){
        momentumService.saveUser(user);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MomentumPOJO loginRequest) {
        boolean isValid = momentumService.validateCredentials(loginRequest.getUsername(), loginRequest.getPassword());

        if (isValid) {
            String token = jwtService.issueToken(loginRequest.getUsername());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", loginRequest.getUsername()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }


    @GetMapping("/coins/{username}")
    public ResponseEntity<CoinsDTO> getCoins(@PathVariable String username) {
        System.out.println(momentumRepo.findByUsername(username).getCoins());
        CoinsDTO coinsDTO = momentumService.getUserCoins(username);
        return ResponseEntity.ok(coinsDTO);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        boolean valid = jwtService.validateToken(token);
        String username = valid ? jwtService.getUsername(token) : null;
        return ResponseEntity.ok(Map.of("valid", valid, "username", username));
    }
}