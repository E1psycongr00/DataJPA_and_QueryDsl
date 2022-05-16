package com.example.mysqltest.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id", nullable = false)
    private Long id;

    @Column(name = "item")
    private String item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
        user.getOrderses().add(this);
    }

    @Builder(setterPrefix = "set", builderMethodName = "getInstance")
    private Orders(String item, User user) {
        this.item = item;
        this.setUser(user);
    }
}