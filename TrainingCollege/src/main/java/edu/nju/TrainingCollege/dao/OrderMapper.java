package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "OrderMapper")
public interface OrderMapper {
    @Select("select * from orders where email = #{email}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "email", column = "email"),
            @Result(property = "college", column = "college"),
            @Result(property = "type", column = "type"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "studentList", column = "id", javaType = List.class,
                    many = @Many(select = "edu.nju.TrainingCollege.dao.StudentMapper.selectByOrder"))
    })
    List<Order> selectByEmail(@Param("email") String email);

    @Select("select * from orders")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "email", column = "email"),
            @Result(property = "college", column = "college"),
            @Result(property = "type", column = "type"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "studentList", column = "id", javaType = List.class,
                    many = @Many(select = "edu.nju.TrainingCollege.dao.StudentMapper.selectByOrder"))
    })
    List<Order> selectAllStudents();

    @Select("select distinct last_insert_id() from orders")
    int selectLastId();

    @Insert("insert into orders (email, college, type, amount) values(#{email}, #{college}, #{type}, #{amount})")
    int insertOrder(@Param("email") String email, @Param("college") int college, @Param("type") String type, @Param("amount") int amount);

    @Delete("delete from orders where id = #{id}")
    int deleteOrder(@Param("id") int id);
}
