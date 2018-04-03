package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component(value = "CourseMapper")
public interface CourseMapper {
    @Select("select * from course where id = #{id}")
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
    Course selectById(@Param("id") int id);

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
    List<Course> selectBySemail(@Param("semail") String semail);

    @Select("select * from course where college = #{college}")
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
    List<Course> selectByCollege(@Param("college") int college);

    @Select("select * from course where college = #{college} and type = #{type}")
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
    List<Course> selectByCollegeAndType(@Param("college") int college, @Param("type") String type);

    @Select("select count(*) from course join classes on id = courseid where courseid = #{courseid}")
    int selectSizeById(@Param("courseid") int courseid);

    @Select("select * from course")
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

    @Insert("insert into course (college, type, temail, size, period, price) values(#{college}, #{type}, #{temail}, #{size}, #{period}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertCourse(Course course);

    @Update("update course set college = #{college}, type = #{type}, temail = #{temail}, size = #{size}, period = #{period}, price = #{price} where id = #{id}")
    int updateCourse(@Param("id") int id, @Param("college") int college, @Param("type") String type, @Param("temail") String temail, @Param("size") int size, @Param("period") int period, @Param("price") int price);
}
