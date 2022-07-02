package com.example.mysqltest.entity;

import com.example.mysqltest.config.TestQueryDslConfig;
import com.example.mysqltest.repository.user.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestQueryDslConfig.class)
public class UserTest {
    
    @Autowired
    JPAQueryFactory jpaQueryFactory;
    
    @Autowired
    UserRepository userRepository;
    
    @Test
    @Sql("/createEntity.sql")
    void UserTest() {
        List<User> userList = userRepository.findAllUser();
        userList.forEach(user -> {
            System.out.println(user.getId() + " " + user.getNickname());
        });
        
    }
}
