package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "StudentMapper")
public interface StudentMapper {
    @Select("select * from student where email = #{email}")
    Student getByEmail(@Param("email") String email);
}
