package com.shantanu.momentum.repo;

import com.shantanu.momentum.model.MomentumPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MomentumRepo extends JpaRepository<MomentumPOJO, String> {
    MomentumPOJO findByUsername(String username);
}