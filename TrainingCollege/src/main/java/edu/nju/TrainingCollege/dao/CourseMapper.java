package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component(value = "CourseMapper")
public interface CourseMapper {
    @Select("select course.* from course join classes on id = courseid where semail = #{semail}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "college", column = "college"),
            @Result(property = "type", column = "type"),
            @Result(property = "temail", column = "temail"),
            @Result(property = "size", column = "size"),
            @Result(property = "period", column = "period"),
            @Result(property = "prize", column = "prize"),
            @Result(property = "scheduleList", column = "id", javaType = List.class,
                    many = @Many(select = "edu.nju.TrainingCollege.dao.ScheduleMapper.selectByCourse")),
            @Result(property = "studentList", column = "id", javaType = List.class,
                    many = @Many(select = "edu.nju.TrainingCollege.dao.StudentMapper.selectByCourse"))
    })
    List<Course> selectBySemail(String semail);

    @Select("select * from course where id = #{id}")
    Course selectById(@Param("id") int id);

    @Select("select course.*, count(*) as tempsize from course join classes on id = courseid where college = #{college} and type = #{type} group by courseid")
    List<Map> selectAvailableCourse(@Param("college") int college, @Param("type") String type);

    @Select("select course.* from course")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "college", column = "college"),
            @Result(property = "type", column = "type"),
            @Result(property = "temail", column = "temail"),
            @Result(property = "size", column = "size"),
            @Result(property = "period", column = "period"),
            @Result(property = "prize", column = "prize"),
            @Result(property = "scheduleList", column = "id", javaType = List.class,
                    many = @Many(select = "edu.nju.TrainingCollege.dao.ScheduleMapper.selectByCourse")),
            @Result(property = "studentList", column = "id", javaType = List.class,
                    many = @Many(select = "edu.nju.TrainingCollege.dao.StudentMapper.selectByCourse"))
    })
    List<Course> selectAllCourses();
}
