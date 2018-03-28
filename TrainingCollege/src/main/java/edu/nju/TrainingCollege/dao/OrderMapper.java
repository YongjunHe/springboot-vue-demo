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

    @Select("select * from orders where college = #{college}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "email", column = "email"),
            @Result(property = "college", column = "college"),
            @Result(property = "type", column = "type"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "studentList", column = "id", javaType = List.class,
                    many = @Many(select = "edu.nju.TrainingCollege.dao.StudentMapper.selectByOrder"))
    })
    List<Order> selectByCollege(@Param("college") int college);

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
    List<Order> selectAllOrders();

    @Insert("insert into orders (email, college, type, amount) values(#{email}, #{college}, #{type}, #{amount})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOrder(Order order);

    @Delete("delete from orders where id = #{id}")
    int deleteOrder(@Param("id") int id);
}
