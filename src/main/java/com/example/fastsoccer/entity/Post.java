package com.example.fastsoccer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;


@Data
@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    Date publicationTime;
    private String content;
    @ManyToOne
    @JoinColumn(name="district_id", nullable=false)
    private District districtEntity;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
