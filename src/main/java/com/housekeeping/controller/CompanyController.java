package com.housekeeping.controller;

import com.housekeeping.entity.EmployRecord;
import com.housekeeping.entity.Result;
import com.housekeeping.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PostMapping("/findAllSelectCompany")
    public Result findAllSelectCompany(){
        return companyService.findAllSelectCompany();
    }

    @PostMapping("/submitEmployRecord")
    public Result submitEmployRecord(@RequestHeader("Authorization") String token, @RequestBody EmployRecord employRecord){
        return companyService.submitEmployRecord(token,employRecord);
    }

    @PostMapping("/findAllEmployRecordByUser")
    public Result findAllEmployRecordByUser(@RequestHeader("Authorization") String token,int page){
        return companyService.findEmployRecordByUser(token,page);
    }

    @PostMapping("/findAllEmployRecordByCompany")
    public Result findAllEmployRecordByCompany(@RequestHeader("Authorization") String token,int page){
        return companyService.findAllEmployRecordByCompany(token,page);
    }

    @PostMapping("/operateEmployRecord")
    public Result operateEmployRecord(@RequestHeader("Authorization") String token,String id,String note,String isAccept){
        if(isAccept.equals("true")){
            return companyService.acceptEmployRecord(token,id,note);
        }
        else if(isAccept.equals("false")) {
            return companyService.rejectEmployRecord(token,id,note);
        }
        return Result.error("What are you doing?");
    }


    @PostMapping("/findServicerAchievement")
    public Result findServicerAchievement(String user){
        return companyService.findServicerAchievement(user);
    }
        final String admin = "/admin";
    @PostMapping(admin+"/findAllCompany")
    public Result findAllCompany(int page){
        return companyService.findAllCompany(page);
    }
}
