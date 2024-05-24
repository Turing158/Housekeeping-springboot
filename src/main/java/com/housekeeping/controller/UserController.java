package com.housekeeping.controller;

import com.housekeeping.entity.Result;
import com.housekeeping.entity.User;
import com.housekeeping.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    HttpSession session;
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public Result login(String user, String password,String code) {
        return userService.login(user,password,code,session);
    }

    @PostMapping("/findUserByToken")
    public Result findUserByToken(@RequestHeader("Authorization") String token) {
        return userService.findUserByToken(token);
    }

    @PostMapping("/register")
    public Result register(String user,String password,String region,String code){
        return userService.register(user,password,code,region,session);
    }
    final String admin = "/admin";
    @PostMapping(admin+"/findAllUser")
    public Result findAllUser(int page){
        return userService.findAllUser(page);
    }

    @PostMapping(admin+"/findAllRole")
    public Result findAllRole(int page){
        return userService.findAllRole(page);
    }
}
