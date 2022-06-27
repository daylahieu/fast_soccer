package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
UserEntity findAllByUsername(String username);
    @Query("SELECT count(r.id) FROM UserEntity r")
    int countReview();
}
