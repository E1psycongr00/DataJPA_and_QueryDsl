package com.example.mysqltest.repository.orders;

import com.example.mysqltest.entity.Orders;

import java.util.List;

public interface OrdersRepositoryCustom {
    List<Orders> getOrdersList();
    
}
