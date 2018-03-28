package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "StudentMapper")
public interface StudentMapper {
    @Select("select * from student where email = #{email}")
    Student selectByEmail(@Param("email") String email);

    @Select("select student.* from student join classes on email = semail where orderid = #{orderid}")
    List<Student> selectByOrder(@Param("orderid") int orderid);

    @Select("select student.* from student join classes on email = semail where courseid = #{courseid}")
    List<Student> selectByCourse(@Param("courseid") int courseid);

    @Select("select s.* from student s join classes c1 on s.email = c1.semail join course c2 on c1.courseid = c2.id where c2.college = #{college}")
    List<Student> selectByCollege(@Param("college") int college);

    @Select("select * from student")
    List<Student> selectAllStudents();

    @Insert("insert into student (email, name, password, level, deposit, point) values(#{email}, #{name}, #{password}, 0, 0, 0)")
    int insertStudent(@Param("email") String email, @Param("name") String name, @Param("password") String password);

    @Update("update student set name = #{name} where email = #{email}")
    int updateNameByEmail(@Param("name") String name, @Param("email") String email);

    @Update("update student set password = #{password} where email = #{email}")
    int updatePasswordByEmail(@Param("password") String password, @Param("email") String email);

    @Update("update student set level = #{level} where email = #{email}")
    int updateLevelByEmail(@Param("level") int level, @Param("email") String email);

    @Update("update student set deposit = #{deposit} where email = #{email}")
    int updateDepositByEmail(@Param("deposit") int deposit, @Param("email") String email);

    @Update("update student set point = #{point} where email = #{email}")
    int updatePointByEmail(@Param("point") int point, @Param("email") String email);
}
