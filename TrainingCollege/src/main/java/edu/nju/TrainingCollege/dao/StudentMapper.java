package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "StudentMapper")
public interface StudentMapper {
    @Select("select * from student where email = #{email}")
    Student getByEmail(@Param("email") String email);

    @Insert("insert into student (email, name, password, level, deposit, point) values(#{email}, #{name}, #{password}, #{level}, 0, 0)")
    int insertStudent(@Param("email") String email, @Param("name") String name, @Param("password") String password, @Param("level") int level);

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
