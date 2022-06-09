package com.example.fastsoccer.repository;

import com.example.fastsoccer.entity.OwnPitch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OwnPitchRepository extends JpaRepository<OwnPitch,Long> {
    @Query("SELECT u FROM OwnPitch u WHERE u.status=0"  )
    List<OwnPitch> findOwnPitchWatting();//sân chờ duyệt
    @Query("SELECT u FROM OwnPitch u WHERE u.status=1"  )
    List<OwnPitch> findOwnPitchSuccess();//sân đã được duyệt --b'1'
}
