package com.example.fastsoccer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yardown")
@Entity
public class Yard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    private int hour7_11;
    private int hour11_15;
    private int hour15_18;
    private int hour18_20;
    private int hour20_22;
    private int hour22_7;
    @ManyToOne
    @JoinColumn(name = "ownpitch_id") // thông qua khóa ngoại
    private OwnPitch ownPitch;
}
