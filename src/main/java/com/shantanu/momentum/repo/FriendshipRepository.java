//package com.shantanu.momentum.repo;
//
//import com.shantanu.momentum.model.Friendship;
//import com.shantanu.momentum.model.MomentumPOJO;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
//
//    @Query("SELECT f.friend FROM Friendship f WHERE f.user.username = :username AND f.status = 'ACCEPTED'")
//    List<MomentumPOJO> findFriendsByUser(@Param("username") String username);
//}

