package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.Yard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YardRepository extends JpaRepository<Yard, Long> {
    @Query("SELECT y FROM Yard y WHERE y.ownPitch.id=:id"  )
    List<Yard> findAllByOwnPitchId(@Param("id") Long id);
}

