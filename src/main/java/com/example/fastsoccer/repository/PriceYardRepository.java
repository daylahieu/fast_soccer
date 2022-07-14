package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.Booking;
import com.example.fastsoccer.entity.PriceYard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceYardRepository extends JpaRepository<PriceYard,Long> {
    List<PriceYard> findAllByYardId_OwnPitch_Id(Long idOwn);
    List<PriceYard> findAllByYardId(Long idYard);
    @Query("select pr from PriceYard pr where  pr.id not in ?1 and pr.yardId.id = ?2 order by pr.id ASC")
    List<PriceYard> findAllYardNotReserved(List<Long> list, Long idYard);

    @Query("select pr from Yard yr \n " +
            "join PriceYard pr on pr.yardId.id = yr.id \n " +
            "where yr.id = ?1 order by pr.id ")
    List<PriceYard> findAllPriceYardByYardID(Long idYard);
}
