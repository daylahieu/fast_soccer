package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.Post;
import com.example.fastsoccer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserEntity(UserEntity userEntity);
}

