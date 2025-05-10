package com.shantanu.momentum.service;

import com.shantanu.momentum.model.MomentumPOJO;
import com.shantanu.momentum.repo.MomentumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MomentumService {
    @Autowired
    private MomentumRepo repo;

    public void saveUser(MomentumPOJO user) {
        repo.save(user);
    }

    public boolean validateCredentials(String username, String password) {
        MomentumPOJO user = repo.findByUsername(username);
        if (user == null) {
            return false;
        } else {
            return user.getPassword().equals(password);
        }
    }
}
