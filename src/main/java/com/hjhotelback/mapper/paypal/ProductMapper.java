package com.hjhotelback.mapper.paypal;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hjhotelback.dto.payment.ReservationItem;

@Mapper
public interface ProductMapper {
    
	// 24.11.29 지은 [완료] : 예약테이블 전체 조회 수정
	@Select("SELECT * FROM reservation")
    List<ReservationItem> findAll();
    
	// 24.11.29 지은 [완료] : 예약ID로 예약내역 조회.
    @Select("SELECT " +
            "r.reservation_id, " +
            "m.name, " +
            "m.email, " +
            "m.phone, " +
            "rmt.name AS room_type_name, " + // room type name
            "rm.room_number, " +
            "rm.floor, " +
            "r.check_in, " +
            "r.check_out, " +
            "r.total_amount " +
            "FROM reservation r " +
            "JOIN member m ON m.member_id = r.member_id " +
            "JOIN room rm ON rm.room_id = r.room_id " +
            "JOIN room_type rmt ON rmt.room_type_id = rm.room_type_id " +
            "WHERE r.reservation_id = #{id}")
    ReservationItem findById(Integer id);
}