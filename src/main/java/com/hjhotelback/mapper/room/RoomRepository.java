package com.hjhotelback.mapper.room;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hjhotelback.dto.room.TypeDetailDto_Client;
import com.hjhotelback.dto.room.CountTypeDto;
import com.hjhotelback.dto.room.RoomAmenityDto;
import com.hjhotelback.dto.room.RoomDto;
import com.hjhotelback.dto.room.RoomTypeDto_Client;
import com.hjhotelback.dto.room.RoomTypeDto;

@Mapper
public interface RoomRepository {
	// ===== 사용자
	 List<RoomTypeDto_Client> AllRoomTypes(Integer typeId);
	// 상세 조회
	 List<TypeDetailDto_Client> detailType(String roomTypeName);
	 // ===== 관리자	
	 //test
	 List<RoomDto> AllRooms();
	 //
	 List<RoomDto> adminresRooms(@Param("date") LocalDate date,@Param("resStatus")String resStatus);
	 List<RoomDto> AllRoomsDate(@Param("date") LocalDate date);
	 int updateStatusRoom(RoomDto roomDto);
	 
	 // =======관리자 - type
	 List<RoomTypeDto> allTypes();
	 // ======= 관리자 -type - count
	 List<CountTypeDto>countTypes();
	// amenity
	 List<RoomAmenityDto> typeAmenities(@Param("TypeName")String TypeName);
	// ResponseEntity<String>updateAmenityByType(String roomTypeName,String amenity,boolean isActive);
	 int updateAmenity(@Param("TypeName")String TypeName,@Param("amenity")String amenity );
}
