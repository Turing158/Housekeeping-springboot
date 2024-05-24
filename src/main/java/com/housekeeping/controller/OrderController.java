package com.housekeeping.controller;

import com.housekeeping.entity.Order;
import com.housekeeping.entity.Result;
import com.housekeeping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public Result createOrder(@RequestHeader("Authorization") String token, @RequestBody Order order) {
        return orderService.createOrder(token, order);
    }
}
