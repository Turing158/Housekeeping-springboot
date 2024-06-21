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

    @PostMapping("/findOrderById")
    public Result findOrderById(@RequestHeader("Authorization") String token, String id) {
        return orderService.findOrderById(token, id);
    }

    @PostMapping("/findAllOrderToUser")
    public Result findAllOrderToUser(@RequestHeader("Authorization") String token, int page) {
        return orderService.findAllOrderToUser(token, page);
    }

    @PostMapping("/findAllOrderToServicer")
    public Result findAllOrderToServicer(@RequestHeader("Authorization") String token, int page) {
        return orderService.findAllOrderToServicer(token, page);
    }

    @PostMapping("/acceptOrder")
    public Result acceptOrder(@RequestHeader("Authorization") String token, String id) {
        return orderService.acceptOrder(token, id);
    }
    @PostMapping("/completeOrder")
    public Result completeOrder(@RequestHeader("Authorization") String token, String id) {
        return orderService.completeOrder(token, id);
    }

    @PostMapping("/evaluateOrder")
    public Result evaluateOrder(@RequestHeader("Authorization") String token, String id, String star, String evaluate) {
        return orderService.evaluateOrder(token, id, star, evaluate);
    }

    @PostMapping("/admin/findAllOrder")
    public Result findAllOrder(int page) {
        return orderService.findAllOrder(page);
    }
}


