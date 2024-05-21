package com.housekeeping.service;


import com.housekeeping.dao.CompanyDao;
import com.housekeeping.entity.Result;
import com.housekeeping.entity.ServicerToUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    public Result findAllServicerOrderByRegion(String region) {
        List<ServicerToUser> servicerToUsers = companyDao.findAllServicerOrderByRegion(region);
        int count = companyDao.countServicer();
        return Result.success(servicerToUsers,count);
    }

}
