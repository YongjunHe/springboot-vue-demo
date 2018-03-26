package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "ScheduleMapper")
public interface ScheduleMapper {
    @Select("select * from schedule where courseid = #{courseid}")
    List<Schedule> selectByCourse(@Param("courseid") String courseid);
}
