package com.housekeeping.dao;

import com.housekeeping.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from housekeeping.user where user = #{user}")
    User findUserByUser(String user);
    @Insert("insert into housekeeping.user(user,password,name,role,region) values(#{user},#{password},#{name},#{role},#{region})")
    int insertUser(User user);

}
