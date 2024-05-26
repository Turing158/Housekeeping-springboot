package com.housekeeping.service;


import com.housekeeping.dao.CompanyDao;
import com.housekeeping.dao.OrderDao;
import com.housekeeping.entity.*;
import com.housekeeping.util.OtherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private OrderDao orderDao;

    public Result findAllServicerOrderByRegion(int page,String region) {
        List<ServicerToUser> servicerToUsers = companyDao.findAllServicerOrderByRegion(region);
        return Result.success( OtherUtil.subList(servicerToUsers,10,page),servicerToUsers.size());
    }

    public Result findServicerByUser(String user) {
        ServicerContent servicerToUsers = companyDao.findServicerByUser(user);
        List<Order> orders = orderDao.findAllOrderByReservedUser(user);
        AtomicInteger historyOrder = new AtomicInteger();
        orders.forEach(order -> {
            if(order.getStatus().equals("completed") || order.getStatus().equals("end")){
                historyOrder.getAndIncrement();
            }
        });
        return Result.success(servicerToUsers,historyOrder.get());
    }

    public Result findAllCompany(int page){
        List<Company> companies = companyDao.findAllCompany();
        return Result.success(OtherUtil.subList(companies,10,page),companies.size());

    }

}
