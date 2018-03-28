package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Classes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "ClassesMapper")
public interface ClassesMapper {
    @Select("select * from classes where semail = #{semail}")
    List<Classes> selectByEmail(@Param("semail") String semail);

    @Select("select count(*) from classes where courseid = #{courseid}")
    int countByCourseid(@Param("courseid") int courseid);

    @Insert("insert into classes (orderid, courseid, semail, score) values(#{orderid}, #{courseid}, #{semail}, 0)")
    int insertClass(@Param("orderid") int orderid, @Param("courseid") int courseid, @Param("semail") String semail);

    @Delete("delete from classes where orderid = #{orderid}")
    int deleteByOrder(@Param("orderid") int orderid);
}
