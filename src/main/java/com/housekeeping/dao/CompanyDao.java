package com.housekeeping.dao;

import com.housekeeping.entity.Company;
import com.housekeeping.entity.ServiceContent;
import com.housekeeping.entity.ServicerContent;
import com.housekeeping.entity.ServicerToUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompanyDao {
    @Select("select s.user as user,u.name as name,u.region as region,s.certificate,u.avatar as certificate,s.experience as experience,c.name as company,s.certificate_info as certificateInfo,s.experience_info as experienceInfo from housekeeping.servicer s join housekeeping.user u on u.user = s.user join housekeeping.company c on s.company = c.id where is_authentication=1 order by u.region=#{region} desc")
    List<ServicerToUser> findAllServicerOrderByRegion(String region);

    @Select("select s.user as user,u.name as name,u.avatar,u.region as region,s.certificate as certificate,s.experience as experience,c.name as company,s.certificate_info as certificateInfo,s.experience_info as experienceInfo from housekeeping.servicer s join housekeeping.user u on u.user = s.user join housekeeping.company c on s.company = c.id where s.user=#{user} and is_authentication=1")
    ServicerContent findServicerByUser(String user);

    @Select("select count(*) from housekeeping.servicer where is_authentication=1 limit 1")
    int countServicer();

    @Select("select id,company.name as companyName,cu.user as user,u.name as name,date from housekeeping.company join housekeeping.company_user cu on company.id = cu.company join housekeeping.user u on cu.user = u.user")
    List<Company> findAllCompany();
}
