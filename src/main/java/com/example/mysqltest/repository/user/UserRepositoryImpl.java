package com.example.mysqltest.repository.user;


import com.example.mysqltest.entity.QUser;
import com.example.mysqltest.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{
    
    private final JPAQueryFactory jpaQueryFactory;
    
    QUser user = QUser.user;
    
    @Override
    public List<User> findAllUser() {
        return jpaQueryFactory.selectFrom(user).fetch();
    }
}
