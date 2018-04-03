package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Classes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "ClassesMapper")
public interface ClassesMapper {
    @Select("select * from classes where semail = #{semail}")
    List<Classes> selectBySemail(@Param("semail") String semail);

    @Select("select c1.* from classes c1 join course c2 on c1.courseid = c2.id where c2.temail = #{temail}")
    List<Classes> selectByTemail(@Param("temail") String temail);

    @Select("select count(*) from classes where courseid = #{courseid}")
    int countByCourseid(@Param("courseid") int courseid);

    @Insert("insert into classes (orderid, courseid, semail, score) values(#{orderid}, #{courseid}, #{semail}, 0)")
    int insertClass(@Param("orderid") int orderid, @Param("courseid") int courseid, @Param("semail") String semail);

    @Update("UPDATE classes SET score = #{score} where courseid = #{courseid} and semail = #{semail}")
    int updateScore(@Param("score") int score, @Param("courseid") int courseid, @Param("semail") String semail);

    @Delete("delete from classes where orderid = #{orderid}")
    int deleteByOrder(@Param("orderid") int orderid);
}
