package com.shantanu.momentum.service;

import com.shantanu.momentum.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private JwtUtil jwtUtil;

    public String issueToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsername(token); // or extractUsername(token)
        return (username != null &&
                username.equals(userDetails.getUsername()) &&
                jwtUtil.isTokenValid(token)); // checks expiration
    }

    public boolean validateToken(String token) {
        return jwtUtil.isTokenValid(token);
    }

    public String getUsername(String token) {
        return jwtUtil.extractUsername(token);
    }
}

