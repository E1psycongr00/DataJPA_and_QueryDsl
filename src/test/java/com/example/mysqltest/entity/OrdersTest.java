package com.example.mysqltest.entity;

import com.example.mysqltest.config.TestQueryDslConfig;
import com.example.mysqltest.repository.OrdersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"})
@Import(TestQueryDslConfig.class)
@Sql("classpath:createEntity.sql")
class OrdersTest {
    @Autowired private EntityManager entityManager;
    @Autowired private OrdersRepository ordersRepository;
    
    @Test
    
    @DisplayName("queryDsl 테스트")
    void queryDslTest() {
        List<Orders> orders = ordersRepository.getOrdersList();
        System.out.println(orders.size());
    }
    @Test
    void emManagerTest() {
        User user = User.getInstance()
                .setNickname("hell")
                .setPassword("world").build();

        Orders orders = Orders.getInstance()
                .setItem("apple")
                .setUser(user)
                .build();

        entityManager.persist(orders);
        entityManager.flush();
    }
    
    
    
}