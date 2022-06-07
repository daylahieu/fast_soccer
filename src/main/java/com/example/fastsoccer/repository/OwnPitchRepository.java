package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.OwnPitch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OwnPitchRepository extends JpaRepository<OwnPitch,Long> {
}
