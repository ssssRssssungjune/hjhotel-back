package com.hjhotelback.mapper.room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
	 List<TypeDetailDto_Client>detailType(String roomTypeName);
	// ===== 관리자	
	 List<RoomDto>adminresRooms(LocalDate date,String resStatus);
	 List<RoomDto>AllRoomsDate(LocalDateTime date);
	 ResponseEntity<String>updateStatusRoom(RoomDto roomDto);
	 int deleteRoom(String roomNumber);
	 // =======관리자 - type
	 List<RoomTypeDto> allTypes();
	 List<TypeDetailDto_Client> detailTypes(String roomType);
	// amenity
	 List<RoomAmenityDto> typeAmenities(String TypeName);
	 int deleteAmenity(String name);
	// ResponseEntity<String>updateAmenityByType(String roomTypeName,String amenity,boolean isActive);
}
