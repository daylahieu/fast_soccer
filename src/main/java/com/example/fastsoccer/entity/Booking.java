package com.example.fastsoccer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
@Data
@Entity
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateBooking;
    @ManyToOne
    @JoinColumn(name = "user_id") // thông qua khóa ngoại
    @JsonIgnore
    private UserEntity userId;
    @ManyToOne
    @JoinColumn(name = "priceYard_id") // thông qua khóa ngoại
    @JsonIgnore
    private PriceYard priceYardID;
    private Boolean status;
}
