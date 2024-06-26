package com.housekeeping.dao;

import com.housekeeping.entity.ServiceContent;
import com.housekeeping.entity.ServiceToUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ServiceDao {
    @Select("select id,title,date,s.region as region,label,u.name as name,u.user as user from housekeeping.service s join housekeeping.user u on u.user = s.user where is_show=1 order by s.region=#{region} desc,date desc")
    List<ServiceToUser> findAllServiceOrderByRegion(String region);

    @Select("select count(*) from housekeeping.service where is_show=1 limit 1")
    int countService();

    @Select("select id,title,date,content,s.region as region,label,u.name as name,u.user as user,u.avatar as avatar from housekeeping.service s join housekeeping.user u on u.user = s.user where id=#{id} and is_show=1")
    ServiceContent findServiceById(int id);

}
