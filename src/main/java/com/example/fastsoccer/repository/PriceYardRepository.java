package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.PriceYard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceYardRepository extends JpaRepository<PriceYard,Long> {
    List<PriceYard> findAllByYardId_OwnPitch_Id(Long idOwn);
    List<PriceYard> findAllByYardId(Long idYard);
}
