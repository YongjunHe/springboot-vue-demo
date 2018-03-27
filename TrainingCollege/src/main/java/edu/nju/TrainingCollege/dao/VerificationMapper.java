package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Verification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "VerificationMapper")
public interface VerificationMapper {
    @Select("select * from verification where email = #{email}")
    Verification selectByEmail(@Param("email") String email);

    @Insert("insert into verification (email, code) values(#{email}, #{code}) on duplicate key update code = #{code}")
    int insert(@Param("email") String email, @Param("code") String code);

    @Update("update verification set code = #{code} where email = #{email}")
    int updateByEmail(@Param("code") String code, @Param("email") String email);
}
