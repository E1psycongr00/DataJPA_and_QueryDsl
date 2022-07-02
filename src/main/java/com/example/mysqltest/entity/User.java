package com.example.mysqltest.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orderses = new ArrayList<>();

    public void setOrderses(List<Orders> orderses) {
        this.orderses = orderses;
    }


    @Builder(setterPrefix = "set", builderMethodName = "getInstance")
    private User(String nickname, String password, int age) {
        this.nickname = nickname;
        this.password = password;
        this.age = age;
    }
}