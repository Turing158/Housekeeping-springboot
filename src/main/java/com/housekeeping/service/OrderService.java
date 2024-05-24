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
            return orderDao.createOrder(order) == 1 ? Result.success("创建成功") : Result.error("创建失败");
        }
        return Result.error("不存在该服务");
    }
}
