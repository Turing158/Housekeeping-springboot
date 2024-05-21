package com.housekeeping.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OtherService {
    public void getImageCode(HttpSession session, HttpServletResponse resp){
        resp.setContentType("image/gif");
        ServletOutputStream sos = null;
        try {
            sos = resp.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GifCaptcha captcha = CaptchaUtil.createGifCaptcha(100,40,4);
        session.setAttribute("code",captcha.getCode());
        System.out.println(captcha.getCode());
        captcha.write(sos);
    }
}
