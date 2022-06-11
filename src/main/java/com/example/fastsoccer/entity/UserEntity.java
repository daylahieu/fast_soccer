package com.example.fastsoccer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

/*
    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)*/
    private String role;
    private Long idOwn;// id chủ sân
    @Enumerated(EnumType.STRING)

    private Provider provider;
}
