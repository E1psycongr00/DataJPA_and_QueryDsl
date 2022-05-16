package com.example.mysqltest.entity;

import com.example.mysqltest.repository.OrdersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"})
class OrdersTest {

    @Autowired private EntityManager entityManager;
    @Autowired private OrdersRepository ordersRepository;

    @Test
    void emManagerTest() {
        User user = User.getInstance()
                .setNickname("hello")
                .setPassword("world").build();

        Orders orders = Orders.getInstance()
                .setItem("apple")
                .setUser(user)
                .build();

        entityManager.persist(orders);
    }
}