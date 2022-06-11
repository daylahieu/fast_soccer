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
@Table(name = "yard")
@Entity
public class Yard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "1h-3h")
    private int h12;
    @Column(name = "3h-5h")
    private int h12;
    @Column(name = "1h-7h")
    private int h12;
    @Column(name = "1h-7h")
    private int h12;
    @Column(name = "1h-9h")
    private int h12;
    @Column(name = "1h-11h")
    private int h12;
    @Column(name = "1h-13h")
    private int h12;
    @Column(name = "1h-15h")
    private int h12;
    @Column(name = "1h-17h")
    private int h12;
    @Column(name = "1h-19h")
    private int h12;
    @Column(name = "1h-21h")
    private int h12;
    @Column(name = "1h-24h")
    private int h12;


    @ManyToOne
    @JoinColumn(name = "ownpitch_id") // thông qua khóa ngoại address_id
    private OwnPitch ownPitch;
}
