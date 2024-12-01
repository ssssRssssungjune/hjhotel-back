package com.hjhotelback.mapper.paypal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hjhotelback.dto.payment.Order;


@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO orders (paypal_order_id, status, reservation_id, amount, created_at ) " +
            "VALUES (#{paypalOrderId}, #{status}, #{reservationId}, #{amount}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Order order);
    
    @Select("SELECT * FROM orders WHERE paypal_order_id = #{paypalOrderId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "product", column = "product_id",
                    one = @One(select = "com.hjhotelback.mapper.payment.ProductMapper.findById"))
    })
    Order findByPaypalOrderId(String paypalOrderId);
    
    @Select("SELECT * FROM orders WHERE reservation_id = #{reservationId}")
    Order findByPaypalReservationId(Integer reservationId);
    
    @Update("UPDATE orders SET status = #{status} WHERE paypal_order_id = #{paypalOrderId}")
    void updateOrderStatus(@Param("paypalOrderId") String paypalOrderId,
                      @Param("status") String status);
}

