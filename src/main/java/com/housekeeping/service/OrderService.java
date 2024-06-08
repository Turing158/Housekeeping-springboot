package com.housekeeping.service;

import com.housekeeping.dao.OrderDao;
import com.housekeeping.dao.ServiceDao;
import com.housekeeping.entity.Order;
import com.housekeeping.entity.Result;
import com.housekeeping.entity.ServiceContent;
import com.housekeeping.util.JwtUtil;
import com.housekeeping.util.OtherUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    ServiceDao serviceDao;

    public Result createOrder(String token,Order order){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        ServiceContent service = serviceDao.findServiceById(order.getService());
        if(service != null){
            LocalDateTime now = LocalDateTime.now();
            String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String id = "D"+ OtherUtil.getRandomNumber(4)+now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+(""+now.getNano()).substring(0,4)+OtherUtil.getRandomNumber(6);
            order.setId(id);
            order.setValue(service.getValue());
            order.setCreateUser(user);
            order.setCreateDate(date);
            order.setReservedUser(service.getUser());
            order.setStatus("paid");
            System.out.println(order);
            return orderDao.createOrder(order) == 1 ? Result.success(order.getId()) : Result.error("创建失败");
        }
        return Result.error("不存在该服务");
    }

    public Result findAllOrderToUser(String token,int page){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        List<Order> orders = orderDao.findAllOrderByCreateUser(user);
        return !orders.isEmpty()?
                Result.success(OtherUtil.subList(orders,10,page),orders.size()):
                Result.error("无数据");
    }

    public Result findAllOrderToServicer(String token,int page){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        List<Order> orders = orderDao.findAllOrderByReservedUser(user);
        return orders.isEmpty() ?
                Result.error("无数据") :
                Result.success(OtherUtil.subList(orders,10,page),orders.size());
    }

    public Result findAllOrder(int page){
        List<Order> orders = orderDao.findAllOrder();
        return orders.isEmpty()?
                Result.error("无数据"):
                Result.success(OtherUtil.subList(orders,10,page),orders.size());
    }

    public Result findOrderById(String token,String id){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        Order order = orderDao.findOrderById(id);
        if(order != null){
            if(order.getCreateUser().equals(user) || order.getReservedUser().equals(user)){
                return Result.success(order);
            }
            return Result.error("无权查看");
        }
        return Result.error("订单不存在");
    }

    public Result acceptOrder(String token,String id){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        Order order = orderDao.findOrderById(id);
        if(order != null){
            if(order.getReservedUser().equals(user)){
                if(order.getStatus().equals("paid")){
                    return orderDao.updateOrderStatus(id,"start") == 1 ? Result.success() : Result.error("接单失败");
                }
                return Result.error("订单状态不正确");
            }
            return Result.error("无权接单");
        }
        return Result.error("订单不存在");
    }


    public Result completeOrder(String token,String id){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        Order order = orderDao.findOrderById(id);
        if(order != null){
            if(order.getReservedUser().equals(user)){
                if(order.getStatus().equals("start")){
                    return orderDao.updateOrderStatus(id,"complete") == 1 ? Result.success() : Result.error("完成订单失败");
                }
                return Result.error("订单状态不正确");
            }
            return Result.error("无权完成订单");
        }
        return Result.error("订单不存在");
    }

    public Result evaluateOrder(String token,String id,String star,String evaluate){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        Order order = orderDao.findOrderById(id);
        if(order != null){
            if(order.getCreateUser().equals(user)){
                if(order.getStatus().equals("complete")){
                    int status1 = orderDao.updateOrderEvaluate(id,star,evaluate);
                    int status2 = orderDao.updateOrderStatus(id,"end");
                    return status1+status2 == 2 ? Result.success() : Result.error("评价失败");
                }
                return Result.error("订单状态不正确");
            }
            return Result.error("无权评价");
        }
        return Result.error("订单不存在");
    }
}
