package com.example.mysqltest.repository;

import com.example.mysqltest.entity.Orders;
import com.example.mysqltest.entity.QOrders;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepositoryCustom{
    
    private final JPAQueryFactory queryFactory;
    
    QOrders orders = QOrders.orders;
    
    
    @Override
    public List<Orders> getOrdersList() {
        return queryFactory
                .selectFrom(orders)
                .fetch();
    }
    
}
