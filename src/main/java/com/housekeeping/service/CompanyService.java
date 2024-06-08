package com.housekeeping.service;


import com.housekeeping.dao.CompanyDao;
import com.housekeeping.dao.OrderDao;
import com.housekeeping.dao.UserDao;
import com.housekeeping.entity.*;
import com.housekeeping.util.JwtUtil;
import com.housekeeping.util.OtherUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    UserDao userDao;


    public Result findAllServicerOrderByRegion(int page,String region) {
        List<ServicerToUser> servicerToUsers = companyDao.findAllServicerOrderByRegion(region);
        return !servicerToUsers.isEmpty() ?
                Result.success( OtherUtil.subList(servicerToUsers,10,page),servicerToUsers.size()) :
                Result.error("无数据")
                ;
    }

    public Result findServicerByUser(String user) {
        ServicerContent servicerToUsers = companyDao.findServicerByUser(user);
        if (servicerToUsers == null) {
            return Result.error("无数据");
        }
        List<Order> orders = orderDao.findAllOrderByReservedUser(user);
        AtomicInteger historyOrder = new AtomicInteger();
        orders.forEach(order -> {
            if(order.getStatus().equals("completed") || order.getStatus().equals("end")){
                historyOrder.getAndIncrement();
            }
        });
        return Result.success(servicerToUsers,historyOrder.get());
    }

    public Result findAllSelectCompany(){
        List<CompanySelect> companies = companyDao.findAllSelectCompany();
        return !companies.isEmpty()?
                Result.success(companies,companies.size()):
                Result.error("无数据");
    }

    public Result findAllCompany(int page){
        List<Company> companies = companyDao.findAllCompany();
        return !companies.isEmpty()?
                Result.success(OtherUtil.subList(companies,10,page),companies.size()):
                Result.error("无数据");
    }

    public Result submitEmployRecord(String token,EmployRecord employRecord){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        employRecord.setUser(user);
        LocalDateTime now = LocalDateTime.now();
        String id = "E"+OtherUtil.getRandomNumber(4)+now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+(""+now.getNano()).substring(0,4)+OtherUtil.getRandomNumber(6);
        employRecord.setId(id);
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        employRecord.setDate(date);
        employRecord.setStatus("commit");
        String certificateFileName = user+"_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+"_certificate";
        String experienceFileName = user+"_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+"_experience";
        String certificateInfo = employRecord.getCertificateInfo();
        String experienceInfo = employRecord.getExperienceInfo();
        String certificateType = OtherUtil.getImageSuffixByBase64(certificateInfo);
        String experienceType = OtherUtil.getImageSuffixByBase64(experienceInfo);
        byte[] certificateBytes = OtherUtil.base64ToByte(certificateInfo);
        byte[] experienceBytes = OtherUtil.base64ToByte(experienceInfo);
        int status1 = OtherUtil.saveFile(certificateBytes,"D:\\EducationalData\\vue\\Housekeeping\\src\\assets\\certificate",certificateFileName+"."+certificateType);
        int status2 = OtherUtil.saveFile(experienceBytes,"D:\\EducationalData\\vue\\Housekeeping\\src\\assets\\experience",experienceFileName+"."+experienceType);
        employRecord.setCertificateInfo(certificateFileName+"."+certificateType);
        employRecord.setExperienceInfo(experienceFileName+"."+experienceType);
        System.out.println(employRecord);
        if(status1+status2 != 2){
            return Result.error("图片上传失败");
        }
        return companyDao.insertEmployRecord(employRecord) == 1 ? Result.success() : Result.error("提交失败");
    }

    public Result findAllEmployRecordByCompany(String token,int page){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        int company = companyDao.findCompanyIdByUser(user);
        List<EmployRecord> employRecords = companyDao.findAllEmployRecordByCompany(company);
        return !employRecords.isEmpty()?
                Result.success(OtherUtil.subList(employRecords,10,page),employRecords.size()):
                Result.error("无数据");
    }

    public Result acceptEmployRecord(String token,String id,String note){
        int status1 = operateEmployRecord(token,id,note,"passed").equals(Result.success()) ? 1 : 0;
        EmployRecord employRecord = companyDao.findEmployRecordById(id);
        int status2 = companyDao.updateServicerIsAuthenticationByUser(employRecord.getUser(),1);
        return status1+status2 == 2 ? Result.success() : Result.error("操作失败");
    }

    public Result rejectEmployRecord(String token,String id,String note){
        return operateEmployRecord(token,id,note,"noPass");
    }
    public Result operateEmployRecord(String token,String id,String note,String status){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        User userObj = userDao.findUserByUser(user);
        if(userObj.getRole().equals("admin") || userObj.getRole().equals("company")){
            String newNote;
            if(userObj.getRole().equals("admin")){
                newNote = "[管理员审核]："+note;
            }
            else{
                newNote = "[公司审核]："+note;
            }
            return companyDao.updateEmployRecordStatusAndNoteById(id,status,newNote) == 1 ? Result.success() : Result.error("操作失败");
        }
        return Result.error("权限不足");
    }

    public Result findServicerAchievement(String user){
        Map<String,Object> result = companyDao.findServicerCountOrderAndSumOrder(user);
        int count = Integer.parseInt(result.get("count").toString());
        double sum = Double.parseDouble(result.get("sum").toString());
        double income = 0;
        if(sum != 0){
            income = sum * 0.9;
        }
        return Result.success(count,sum,income);
    }


    public Result findEmployRecordByUser(String token,int page){
        Claims claims = JwtUtil.parseJWT(token);
        String user = (String) claims.get("USER");
        List<EmployRecord> employRecords = companyDao.findAllEmployRecordByUser(user);
        return !employRecords.isEmpty()?
                Result.success(OtherUtil.subList(employRecords,10,page),employRecords.size()):
                Result.error("无数据");
    }

}
