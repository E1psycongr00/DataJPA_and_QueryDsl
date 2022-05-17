package com.example.mysqltest.repository;

import com.example.mysqltest.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long>,
        OrdersRepositoryCustom {
}