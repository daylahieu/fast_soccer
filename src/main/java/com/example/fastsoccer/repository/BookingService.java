package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.Booking;
import com.example.fastsoccer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface BookingService extends JpaRepository<Booking, Long> {
@Query("select b from Booking b where b.userId = :userId and b.status = true")
    List<Booking> findAllByUserId1(@Param("userId")UserEntity userId);
    List<Booking> findAllByPriceYardID_YardId_OwnPitch_IdAndStatusIsTrue(Long userId);
/*
* @author: HieuMM
* @since: 06-Jul-22 11:39 AM
* @description-VN:  hiển thị sân đã đặt trong ngày
* @description-EN:
* @param:
* */
    @Query("select b.priceYardID.id from Booking b where b.dateBooking=?1 and b.priceYardID.yardId.id=?2 and b.status = true")
    List<Long> findAllPriceYardIsBooking(Date dateB,Long id);


}

