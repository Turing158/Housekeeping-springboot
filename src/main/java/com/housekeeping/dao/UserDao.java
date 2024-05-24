package com.housekeeping.dao;

import com.housekeeping.entity.Role;
import com.housekeeping.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select * from housekeeping.user where user = #{user}")
    User findUserByUser(String user);

    @Select("select user,name,role.rolename as role,region,avatar from housekeeping.user join housekeeping.role on user.role = role.role")
    List<User> findAllUser();

    @Select("select * from housekeeping.role")
    List<Role> findAllRole();

    @Insert("insert into housekeeping.user(user,password,name,role,region,avatar) values(#{user},#{password},#{name},#{role},#{region},'default.jpg')")
    int insertUser(User user);


}
