package com.housekeeping.service;

import com.housekeeping.dao.ServiceDao;
import com.housekeeping.entity.Result;
import com.housekeeping.entity.ServiceContent;
import com.housekeeping.entity.ServiceToUser;
import com.housekeeping.util.OtherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {
    @Autowired
    private ServiceDao serviceDao;

    public Result findAllServiceOrderByRegion(int page,String region) {
        List<ServiceToUser> serviceToUsers = serviceDao.findAllServiceOrderByRegion(region);
        return serviceToUsers.isEmpty()?
                Result.error("无数据"):
                Result.success(OtherUtil.subList(serviceToUsers,10,page),serviceToUsers.size());
    }

    public Result findServiceById(int id) {
        ServiceContent service = serviceDao.findServiceById(id);
        return Result.success(service);
    }

}
