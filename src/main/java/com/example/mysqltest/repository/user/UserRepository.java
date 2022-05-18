package com.example.mysqltest.repository.user;

import com.example.mysqltest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom{
}