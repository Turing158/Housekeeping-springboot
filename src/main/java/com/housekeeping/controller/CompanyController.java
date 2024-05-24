package com.housekeeping.controller;

import com.housekeeping.entity.Result;
import com.housekeeping.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/findAllServicerOrderByRegion")
    public Result findAllServicerOrderByRegion(int page,String region) {
        return companyService.findAllServicerOrderByRegion(page,region);
    }

    @PostMapping("/findServicerByUser")
    public Result findServicerByUser(String user) {
        return companyService.findServicerByUser(user);
    }



    final String admin = "/admin";
    @PostMapping(admin+"/findAllCompany")
    public Result findAllCompany(int page){
        return companyService.findAllCompany(page);
    }
}
