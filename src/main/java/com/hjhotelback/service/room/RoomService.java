package com.hjhotelback.service.room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.room.TypeDetailDto_Client;
import com.hjhotelback.dto.room.RoomAmenityDto;
import com.hjhotelback.dto.room.RoomDto;
import com.hjhotelback.dto.room.RoomTypeDto_Client;
import com.hjhotelback.dto.room.RoomStatus;
import com.hjhotelback.dto.room.RoomTypeDto;
import com.hjhotelback.mapper.room.RoomRepository;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
//	======== 사용자 

	public List<RoomTypeDto_Client> getRoomTypes(){
		return roomRepository.AllRoomTypes();
	}
	
	public List<TypeDetailDto_Client> getDetailType(String roomTypeName){
		return roomRepository.detailType(roomTypeName);
	}
	
//  ======== 관리	자
	
	// 24.11.27 진주 : 관리자- 날짜,상태로 객실 리스트 불러오기
	public List<RoomDto> getAdminRooms(LocalDate date,String resStatus){
		return roomRepository.adminresRooms(date,resStatus);
	}
	
	// 24.11.27 진주 : 관리자- 날짜로 객실 리스트 불러오기
	public List<RoomDto> getAllDateRooms(LocalDateTime date){
		return roomRepository.AllRoomsDate(date);
	}
	
	// 24.11.27 진주 : 관리자- 객실 상태 변경
	public ResponseEntity<String> UpdateStatus(RoomDto roomDto){
		return roomRepository.updateStatusRoom(roomDto);
	}

// ======= 타입
	public List<RoomTypeDto> getTypes(){
		return roomRepository.allTypes();
	}
	
// ======= 타입별 amenity 
	public List<RoomAmenityDto> getAmenity(String TypeName){
		return roomRepository.typeAmenities(TypeName);
	}

// ======= 타입 별 amenity 활성화
	
	
	
}