package com.housekeeping.service;

import com.housekeeping.dao.UserDao;
import com.housekeeping.entity.Result;
import com.housekeeping.entity.Role;
import com.housekeeping.entity.User;
import com.housekeeping.exception.ErrorException;
import com.housekeeping.util.AECSecurity;
import com.housekeeping.util.JwtUtil;
import com.housekeeping.util.OtherUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    AECSecurity aecSecurity;
    @Autowired
    UserDao userDao;

    public Result login(String user, String password, String code, HttpSession session) {
        String codeSession = (String) session.getAttribute("code");
        System.out.println(codeSession);
        System.out.println(code);
        if(code.equals(codeSession)){
            User userObj = userDao.findUserByUser(user);
            if(userObj != null){
                if(userObj.getPassword().equals(aecSecurity.encrypt(password))){
                    Map<String, Object> claim = new HashMap<>();
                    claim.put("USER", userObj.getUser());
                    claim.put("ROLE", userObj.getRole());
                    String token = JwtUtil.generateJWT(claim);
                    return Result.success(token);
                }
                throw new ErrorException("密码错误");
            }
            throw new ErrorException("用户不存在");
        }
        throw new ErrorException("验证码错误");
    }

    public Result findUserByToken(String token){
        Claims claim = JwtUtil.parseJWT(token);
        String user = (String) claim.get("USER");
        User userObj = userDao.findUserByUser(user);
        return Result.success(userObj);
    }

    public Result register(String user,String password,String code,String region,HttpSession session){
        String codeSession = (String) session.getAttribute("code");
        if(code.equals(codeSession)){
            User userObj = userDao.findUserByUser(user);
            if(userObj == null){
                String name = "新用户"+ OtherUtil.getCode(6);
                User userNew = new User(user,aecSecurity.encrypt(password),name,"user",region,null);
                int r = userDao.insertUser(userNew);
                return r == 1 ? Result.success() : Result.error("注册失败");
            }
            throw new ErrorException("用户已存在");
        }
        throw new ErrorException("验证码错误");
    }

    public Result findAllUser(int page){
        List<User> userObj = userDao.findAllUser();
        return Result.success(OtherUtil.subList(userObj,10,page),userObj.size());
    }

    public Result findAllRole(int page){
        List<Role> roles = userDao.findAllRole();
        return Result.success(OtherUtil.subList(roles,10,page),roles.size());
    }

    public Result updateUserInfo(String user,String name,String region,String code,HttpSession session){
        String codeSession = (String) session.getAttribute("code");
        if(codeSession.equals(code)){
            User userObj = new User();
            userObj.setUser(user);
            userObj.setName(name);
            userObj.setRegion(region);
            int r = userDao.updateUser(userObj);
            return r == 1 ? Result.success() : Result.error("更新失败");
        }
        return Result.error("验证码错误");
    }
    @Value("${avatar.path}")
    String avatarPath = "D:/EducationalData/vue/Housekeeping/src/assets/avatar";
    public Result uploadAvatar(String token,String avatar){
        Claims claim = JwtUtil.parseJWT(token);
        String user = (String) claim.get("USER");
        User userObj = userDao.findUserByUser(user);
        byte[] bytes = OtherUtil.base64ToByte(avatar);
        String suffix = OtherUtil.getImageSuffixByBase64(avatar);
        LocalDateTime now = LocalDateTime.now();
        String filename = user +now.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss"))+(""+now.getNano()).substring(0,4)+ "." + suffix;
        OtherUtil.saveFile(bytes,avatarPath,filename);
        int r = userDao.updateAvatar(user,filename);
        if(userObj != null && !userObj.getAvatar().equals("default.jpg")){
            File file = new File(avatarPath+"/"+userObj.getAvatar());
            file.delete();
        }
        return r == 1 ? Result.success(filename) : Result.error("更新失败");
    }

    public Result updateUserRole(String user,String oldPassword,String newPassword,String code,HttpSession session){
        String codeSession = (String) session.getAttribute("code");
        if(codeSession.equals(code)){
            User userObj = userDao.findUserByUser(user);
            if(userObj.getPassword().equals(aecSecurity.encrypt(oldPassword))){
                userObj.setPassword(aecSecurity.encrypt(newPassword));
                int r = userDao.updateUser(userObj);
                return r == 1 ? Result.success() : Result.error("更新失败");
            }
            return Result.error("旧密码错误");
        }
        return Result.error("验证码错误");
    }
}
