package com.example.mysqltest.repository;

import com.example.mysqltest.entity.Orders;

import java.util.List;

public interface OrdersRepositoryCustom {
    List<Orders> getOrdersList();
    
}
