package com.example.mysqltest.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;


import javax.persistence.EntityManager;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"})
public class dataSqlTest {

    @Autowired
    EntityManager em;

    @Test
    @Sql("/createEntity.sql")
    @Sql("/createEntity2.sql")
    void dataSqlTest() {
        User user = em.find(User.class, 2L);
        System.out.println(user.getNickname());
    }
}
