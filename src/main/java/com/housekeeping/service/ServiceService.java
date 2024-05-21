package com.housekeeping.service;

import cn.hutool.core.util.PageUtil;
import com.housekeeping.dao.ServiceDao;
import com.housekeeping.entity.Result;
import com.housekeeping.entity.ServiceToUser;
import com.housekeeping.util.OtherUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {
    @Autowired
    private ServiceDao serviceDao;

    public Result findAllServiceOrderByRegion(int page,String region) {
        List<ServiceToUser> serviceToUsers = serviceDao.findAllServiceOrderByRegion(region);
        return Result.success(OtherUtil.subList(serviceToUsers,10,page),serviceToUsers.size());
    }

}
