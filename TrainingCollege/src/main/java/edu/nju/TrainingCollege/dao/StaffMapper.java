package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Staff;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "StaffMapper")
public interface StaffMapper {
    @Select("select * from staff where email = #{email}")
    Staff selectByEmail(@Param("email") String email);

    @Select("select * from staff where college = #{college}")
    List<Staff> selectByCollege(@Param("college") int college);

    @Select("select * from staff")
    List<Staff> selectAllStaffs();

    @Insert("insert into staff (email, name, password, title, college) values(#{email}, #{name}, #{password}, #{title}, #{college})")
    int insertStaff(@Param("email") String email, @Param("name") String name, @Param("password") String password, @Param("title") String title, @Param("college") int college);

    @Update("update staff set name = #{name}, password = #{password}, title = #{title}, college = #{college} where email = #{email}")
    int updateStaff(@Param("email") String email, @Param("name") String name, @Param("password") String password, @Param("title") String title, @Param("college") int college);
}
