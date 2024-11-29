package com.hjhotelback.mapper.room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.http.ResponseEntity;

import com.hjhotelback.dto.room.TypeDetailDto_Client;
import com.hjhotelback.dto.room.RoomAmenityDto;
import com.hjhotelback.dto.room.RoomDto;
import com.hjhotelback.dto.room.RoomTypeDto_Client;
import com.hjhotelback.dto.room.RoomStatus;
import com.hjhotelback.dto.room.RoomTypeDto;

@Mapper
public interface RoomRepository {
	// ===== 사용자
	 List<RoomTypeDto_Client>AllRoomTypes();
	// 상세 조회
	 List<TypeDetailDto_Client> detailType(String roomTypeName);
	 // ===== 관리자	
	 List<RoomDto>adminresRooms(@Param("date") LocalDate date,@Param("resStatus")String resStatus);
	 List<RoomDto>AllRoomsDate(@Param("date") LocalDate date);
	 ResponseEntity<String>updateStatusRoom(RoomDto roomDto);
	 
	 // =======관리자 - type
	 List<RoomTypeDto> allTypes();
	// amenity
	 List<RoomAmenityDto> typeAmenities(@Param("TypeName")String TypeName);
	// ResponseEntity<String>updateAmenityByType(String roomTypeName,String amenity,boolean isActive);
	 int updateAmenity(@Param("TypeName")String TypeName,@Param("amenity")String amenity );
}
