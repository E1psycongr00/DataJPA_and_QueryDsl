package com.example.mysqltest.controller;

import com.example.mysqltest.entity.Orders;
import com.example.mysqltest.repository.orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    
    private final OrdersRepository ordersRepository;
    @GetMapping("/hello")
    public List<Orders> getHello() {
        return ordersRepository.getOrdersList();
    }
    
    @PostMapping("/hello")
    public Orders postHello(@RequestBody Orders orders) {
        return ordersRepository.save(orders);
    }
}
