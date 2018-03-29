package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.College;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "CollegeMapper")
public interface CollegeMapper {
    @Select("select * from college where id = #{id}")
    College selectById(@Param("id") int id);

    @Select("select * from college")
    List<College> selectAllColleges();

    @Insert("insert into college (password, location, finance, level) values(#{password}, #{location}, 0, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertCollege(College college);

    @Update("update college set password = #{password} where id = #{id}")
    int updatePasswordById(@Param("id") int id, @Param("password") String password);

    @Update("update college set location = #{location} where id = #{id}")
    int updateLocationById(@Param("id") int id, @Param("location") String location);

    @Update("update college set finance = #{finance} where id = #{id}")
    int updateFinanceById(@Param("id") int id, @Param("finance") int finance);

    @Update("update college set level = #{level} where id = #{id}")
    int updateLevelById(@Param("id") int id, @Param("level") int level);
}
