package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Schedule;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "ScheduleMapper")
public interface ScheduleMapper {
    @Select("select * from schedule where courseid = #{courseid}")
    List<Schedule> selectByCourse(@Param("courseid") String courseid);

    @Insert("insert into schedule (courseid, starttime, endtime) values(#{courseid}, #{starttime}, #{endtime})")
    int insertSchedule(Schedule schedule);

    @Update("update schedule set starttime = #{starttime}, endtime = #{endtime} where courseid = #{courseid}")
    int updateSchedule(Schedule schedule);
}
