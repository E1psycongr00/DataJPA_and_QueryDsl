package com.example.mysqltest.repository.user;

import com.example.mysqltest.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findAllUser();
}
