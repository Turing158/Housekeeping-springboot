package com.housekeeping.filter;

import com.housekeeping.config.SecurityConfig;
import com.housekeeping.entity.Result;
import com.housekeeping.util.JsonOperation;
import com.housekeeping.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Value("${direct.access.url}")
    String[] directAccessUrl = {};
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        String origin = req.getHeader("Origin");
        resp.setHeader("Access-Control-Allow-Origin", origin);
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token,Origin,Content-Type,Accept");
        if(HttpMethod.OPTIONS.toString().equals(req.getMethod())){
            resp.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }
        String url = req.getRequestURL().toString();
        for (int i = 0; i < directAccessUrl.length; i++) {
            if(url.contains(directAccessUrl[i])){
                log.info("公共操作::{}", url);
                filterChain.doFilter(req, resp);
                return;
            }
        }
        String token = req.getHeader("Authorization");
        if(token == null || token.isEmpty()){
            log.info("token为空，未登录，拒绝访问：{}",url);
            resp.getWriter().write(JsonOperation.toJson(Result.error(500,"未登录,请先登录")));
            return;
        }

        try {
            JwtUtil.parseJWT(token);
        }catch (ExpiredJwtException e){
            log.info("token已过期");
            resp.getWriter().write(JsonOperation.toJson(Result.error(500,"TOKEN_EXPIRED")));
            return;
        }catch (MalformedJwtException e){
            log.info("token解析失败，拒绝访问：{}",url);
            resp.getWriter().write(JsonOperation.toJson(Result.error(500,"token解析失败,请先登录")));
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(token,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(req, resp);
    }
}
