package com.housekeeping.dao;

import com.housekeeping.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {

    @Insert("insert into housekeeping.order (id,create_user,create_date,service,reserved_user,reserved_date,reserved_place,note,value,status,user_evaluate_star,user_evaluate) values (#{id},#{createUser},#{createDate},#{service},#{reservedUser},#{reservedDate},#{reservedPlace},#{note},#{value},#{status},#{userEvaluateStar},#{userEvaluate})")
    int createOrder(Order order);
}
