package com.hjhotelback.mapper.room;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;

import com.hjhotelback.dto.room.RoomDto;
import com.hjhotelback.dto.room.RoomDto_Client;
import com.hjhotelback.dto.room.RoomStatus;

@Mapper
public interface RoomRepository {
	// ===== 사용자
	 List<RoomDto>AllRooms();
	// ===== 관리자	
	 List<RoomDto>adminAllRooms();
	 List<RoomDto>adminFloorRoom(Integer floor);
	 List<RoomDto>allStatus(RoomStatus roomStatus);
	 List<RoomDto>adminDetailRoom(String roomNumber);
	 ResponseEntity<String>updateStatusRoom(RoomDto roomDto);
	 int deleteRoom(String roomNumber);

}
