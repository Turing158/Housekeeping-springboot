package com.housekeeping.controller;

import com.housekeeping.entity.Result;
import com.housekeeping.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @PostMapping("/findAllServiceOrderByRegion")
    public Result findAllServiceOrderByRegion(int page,String region) {
        return serviceService.findAllServiceOrderByRegion(page,region);
    }
}
