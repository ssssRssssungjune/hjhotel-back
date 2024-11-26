package com.hjhotelback.mapper.room;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;

import com.hjhotelback.dto.room.DetailRoomType;
import com.hjhotelback.dto.room.RoomAmenityDto;
import com.hjhotelback.dto.room.RoomDto;
import com.hjhotelback.dto.room.RoomDto_Client;
import com.hjhotelback.dto.room.RoomStatus;
import com.hjhotelback.dto.room.RoomTypeDto;

@Mapper
public interface RoomRepository {
	// ===== 사용자
	 List<RoomDto_Client>AllRooms();
	// ===== 관리자	
	 List<RoomDto>adminAllRooms();
	 List<RoomDto>adminFloorRoom(Integer floor);
	 List<RoomDto>allStatus(RoomStatus roomStatus);
	 List<RoomDto>adminDetailRoom(String roomNumber);
	 ResponseEntity<String>updateStatusRoom(RoomDto roomDto);
	 int deleteRoom(String roomNumber);
	 List<RoomTypeDto> allTypes();
	 List<DetailRoomType> detailTypes(String roomType);
	 List<RoomAmenityDto> typeAmenities(String TypeName);
	 int deleteAmenity(String name);
	// ResponseEntity<String>updateAmenityByType(String roomTypeName,String amenity,boolean isActive);
}
