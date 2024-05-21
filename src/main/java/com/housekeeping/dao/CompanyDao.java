package com.housekeeping.dao;

import com.housekeeping.entity.ServicerToUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompanyDao {
    @Select("select s.user as user,u.name as name,u.region as region,s.certificate as certificate,s.experience as experience,c.name as company,s.certificate_info as certificateInfo,s.experience_info as experienceInfo from housekeeping.servicer s join housekeeping.user u on u.user = s.user join housekeeping.company c on s.company = c.id where is_authentication=1 order by u.region=#{region} desc")
    List<ServicerToUser> findAllServicerOrderByRegion(String region);

    @Select("select count(*) from housekeeping.servicer where is_authentication=1 limit 1")
    int countServicer();
}
