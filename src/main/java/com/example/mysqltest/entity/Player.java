package com.example.mysqltest.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "sports_category")
    private String sportsCategory;
    
    @Builder(setterPrefix = "set", builderMethodName = "getInstance")
    private Player(String name, Integer number, String sportsCategory) {
        this.name = name;
        this.age = age;
        this.sportsCategory = sportsCategory;
    }
}