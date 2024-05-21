package com.housekeeping.controller;

import com.housekeeping.service.OtherService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtherController {
    @Autowired
    private OtherService otherService;

    @GetMapping("/getImageCode")
    public void getImageCode(HttpSession session, HttpServletResponse resp) {
        otherService.getImageCode(session,resp);
    }

    @PostMapping("/test")
    public String test(){
        return "test";
    }
}
