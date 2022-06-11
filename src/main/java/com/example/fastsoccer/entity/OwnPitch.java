package com.example.fastsoccer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ownpitch")
@Entity
public class OwnPitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //thông tin chủ sân bóng
    @Column(name = "nameOwn")
    private String name;//tên chủ sân
    @Column(name = "phone")
    private String phone;//sdt chủ sân
    @Column(name = "mail")
    private String mail;//mail chủ sân

    //thông tin sân bóng
    @Column(name = "name")

    private String namePitch;//tên sân
   /* @ManyToOne
    @JoinColumn(name="city_id", nullable=false)// thành phố
    private City city;*/
    @ManyToOne
    @JoinColumn(name="district_id", nullable=false)//huyện
    private District district;
    /*@ManyToOne
    @JoinColumn(name="ward_id", nullable=false)//xã
    private WardEntity wardEntity;*/
    @Column(name = "address")//địa chỉ chính xác
    private String address;
    @Column(name = "stkbank")//stk ngân hàng
    private String stkbank;
    @Column(name = "bank")//tên ngân hàng
    private String bank;
    @Column(name = "img1")//ảnh sân 1
    private String img1;
    @Column(name = "img2")//ảnh sân 2
    private String img2;
    @Column(name = "img3")//ảnh sân 3
    private String img3;
    @Column(name = "service")// dịch vụ đi kèm
    private String service;
    @Column(name = "status",nullable = true)//trạng thái chờ duyệt
    private Boolean status;
    @Column(name = "createacc",nullable = true)//trạng thái chờ duyệt
    private String createacc;
    @OneToMany(mappedBy = "ownPitch", cascade = CascadeType.ALL)
    private List<Yard> yardList;
}
