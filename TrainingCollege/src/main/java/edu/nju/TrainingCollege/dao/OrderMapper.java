package edu.nju.TrainingCollege.dao;

import edu.nju.TrainingCollege.domain.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
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
            @Result(property = "status", column = "status"),
            @Result(property = "creattime", column = "creattime"),
            @Result(property = "paytime", column = "paytime"),
            @Result(property = "settletime", column = "settletime"),
            @Result(property = "studentList", column = "id", javaType = List.class,
                    many = @Many(select = "edu.nju.TrainingCollege.dao.StudentMapper.selectByOrder"))
    })
    List<Order> selectByEmail(@Param("email") String email);

    @Select("select * from orders where id = #{id}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "email", column = "email"),
            @Result(property = "college", column = "college"),
            @Result(property = "type", column = "type"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "status", column = "status"),
            @Result(property = "creattime", column = "creattime"),
            @Result(property = "paytime", column = "paytime"),
            @Result(property = "settletime", column = "settletime"),
            @Result(property = "studentList", column = "id", javaType = List.class,
                    many = @Many(select = "edu.nju.TrainingCollege.dao.StudentMapper.selectByOrder"))
    })
    Order selectById(@Param("id") int id);

    @Select("select * from orders where college = #{college}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "email", column = "email"),
            @Result(property = "college", column = "college"),
            @Result(property = "type", column = "type"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "status", column = "status"),
            @Result(property = "creattime", column = "creattime"),
            @Result(property = "paytime", column = "paytime"),
            @Result(property = "settletime", column = "settletime"),
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
            @Result(property = "status", column = "status"),
            @Result(property = "creattime", column = "creattime"),
            @Result(property = "paytime", column = "paytime"),
            @Result(property = "settletime", column = "settletime"),
            @Result(property = "studentList", column = "id", javaType = List.class,
                    many = @Many(select = "edu.nju.TrainingCollege.dao.StudentMapper.selectByOrder"))
    })
    List<Order> selectAllOrders();

    @Insert("insert into orders (email, college, type, amount, status, creattime) values(#{email}, #{college}, #{type}, #{amount}, #{status}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertOrder(Order order);

    @Update("UPDATE orders SET paytime = NOW() WHERE TIMESTAMPDIFF(MINUTE, creattime, NOW())<=15")
    int updatePaytimeById(@Param("id") int id);

    @Update("update orders set settletime = NOW() where id = #{id}")
    int updateSettletimeById(@Param("id") int id);

    @Update("update orders set status = #{status} where id = #{id}")
    int updateStatusById(@Param("status") int status, @Param("id") int id);

    @Delete("delete from orders where id = #{id}")
    int deleteOrderById(@Param("id") int id);

    @Delete("delete from orders WHERE TIMESTAMPDIFF(MINUTE, creattime, NOW())>15 and status = 0")
    int deleteOrderByTime();
}
