package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistricRepository extends JpaRepository<District,Long> {

}
