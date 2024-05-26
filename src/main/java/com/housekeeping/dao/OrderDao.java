package com.housekeeping.dao;

import com.housekeeping.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderDao {

    @Select("select " +
            "order_form.id,create_user as createUser,cu.name as createName,create_date as createDate,service,s.title as serviceName,reserved_user as reservedUser,ru.name as reservedName,reserved_date as reservedDate,reserved_place as reservedPlace,note,order_form.value as value,status " +
            "from housekeeping.order_form " +
            "join housekeeping.user cu on cu.user = order_form.create_user " +
            "join housekeeping.user ru on ru.user = order_form.reserved_user " +
            "join housekeeping.service s on s.id = order_form.service " +
            "where create_user = #{createUser} " +
            "order by status='complete' desc, status='start' desc,status='paid' desc,create_date desc")
    List<Order> findAllOrderByCreateUser(String createUser);

    @Select("select " +
            "order_form.id,create_user as createUser,cu.name as createName,create_date as createDate,service,s.title as serviceName,reserved_user as reservedUser,reserved_phone as reservedPhone,ru.name as reservedName,reserved_date as reservedDate,reserved_place as reservedPlace,note,order_form.value as value,status,user_evaluate_star as userEvaluateStar,user_evaluate as userEvaluate " +
            "from housekeeping.order_form " +
            "join housekeeping.user cu on cu.user = order_form.create_user " +
            "join housekeeping.user ru on ru.user = order_form.reserved_user " +
            "join housekeeping.service s on s.id = order_form.service " +
            "where reserved_user = #{reservedUser} " +
            "order by status,create_date desc")
    List<Order> findAllOrderByReservedUser(String reservedUser);

    @Select("select " +
            "order_form.id,create_user as createUser,cu.name as createName,create_date as createDate,service,s.title as serviceName,reserved_user as reservedUser,ru.name as reservedName,reserved_phone as reservedPhone,reserved_date as reservedDate,reserved_place as reservedPlace,note,order_form.value as value,status " +
            "from housekeeping.order_form " +
            "join housekeeping.user cu on cu.user = order_form.create_user " +
            "join housekeeping.user ru on ru.user = order_form.reserved_user " +
            "join housekeeping.service s on s.id = order_form.service " +
            "order by status,create_date desc")
    List<Order> findAllOrder();

    @Select("select " +
            "order_form.id,create_user as createUser,cu.name as createName,create_date as createDate,service,s.title as serviceName,reserved_user as reservedUser,reserved_phone as reservedPhone,ru.name as reservedName,reserved_date as reservedDate,reserved_place as reservedPlace,note,order_form.value as value,status " +
            "from housekeeping.order_form " +
            "join housekeeping.user cu on cu.user = order_form.create_user " +
            "join housekeeping.user ru on ru.user = order_form.reserved_user " +
            "join housekeeping.service s on s.id = order_form.service " +
            "where order_form.id = #{id}")
    Order findOrderById(String id);


    @Insert("insert into housekeeping.order_form " +
            "(id,create_user,create_date,service,reserved_user,reserved_phone,reserved_date,reserved_place,note,value,status,user_evaluate_star,user_evaluate)" +
            " values (#{id},#{createUser},#{createDate},#{service},#{reservedUser},#{reservedPhone},#{reservedDate},#{reservedPlace},#{note},#{value},#{status},#{userEvaluateStar},#{userEvaluate})")
    int createOrder(Order order);

    @Update("update housekeeping.order_form set status = #{status} where id = #{id}")
    int updateOrderStatus(String id, String status);

    @Update("update housekeeping.order_form set user_evaluate_star = #{userEvaluateStar}, user_evaluate = #{userEvaluate} where id = #{id}")
    int updateOrderEvaluate(String id, String userEvaluateStar, String userEvaluate);

}
