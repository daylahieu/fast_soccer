package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.Booking;
import com.example.fastsoccer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingService extends JpaRepository<Booking, Long> {

    List<Booking> findAllByUserId(UserEntity userId);
    List<Booking> findAllByPriceYardID_YardId_OwnPitch_Id(Long userId);

}

