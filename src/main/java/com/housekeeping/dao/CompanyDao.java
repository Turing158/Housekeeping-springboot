package com.housekeeping.dao;

import com.housekeeping.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

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

    @Select("select * from housekeeping.company")
    List<CompanySelect> findAllSelectCompany();

    @Select("select company from housekeeping.company_user where user=#{user} limit 1")
    int findCompanyIdByUser(String user);

    @Select("select * from housekeeping.employ_record")
    List<EmployRecord> findAllEmployRecord();

    @Select("select * from housekeeping.employ_record where id=#{id}")
    EmployRecord findEmployRecordById(String id);

    @Select("select e.id as id,user,truth_name as truthName,c.name as company,experience,experience_info as experienceInfo,certificate,certificate_info as certificateInfo,status,e.date,e.note from housekeeping.employ_record e join housekeeping.company c on e.company = c.id where user=#{user} order by e.date desc")
    List<EmployRecord> findAllEmployRecordByUser(String user);

    @Select("select e.id as id,user,truth_name as truthName,c.name as company,experience,experience_info as experienceInfo,certificate,certificate_info as certificateInfo,status,e.date,e.note from housekeeping.employ_record e join housekeeping.company c on e.company = c.id where company=#{company} order by e.status = 'commit' desc,e.status = 'passed' desc ,e.date desc")
    List<EmployRecord> findAllEmployRecordByCompany(int company);

    @Select("select count(*) as count,sum(value) as sum from housekeeping.order_form where reserved_user = #{user} and status = 'complete' or status = 'end' limit 1")
    Map<String,Object> findServicerCountOrderAndSumOrder(String user);

    @Insert("insert into housekeeping.employ_record(id,user,truth_name,company,experience,certificate,certificate_info,experience_info,status,date,note) values(#{id},#{user},#{truthName},#{company},#{experience},#{certificate},#{certificateInfo},#{experienceInfo},#{status},#{date},#{note})")
    int insertEmployRecord(EmployRecord employRecord);

    @Update("update housekeeping.employ_record set status=#{status},note=#{note} where id=#{id}")
    int updateEmployRecordStatusAndNoteById(String id,String status,String note);

    @Update("update housekeeping.servicer set is_authentication=#{is} where user=#{user}")
    int updateServicerIsAuthenticationByUser(String user,int is);

}
