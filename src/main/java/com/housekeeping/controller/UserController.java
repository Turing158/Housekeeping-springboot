package com.housekeeping.controller;

import com.housekeeping.entity.Result;
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
}
