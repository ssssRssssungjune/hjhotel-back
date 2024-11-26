package com.hjhotelback.service.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.room.DetailRoomType;
import com.hjhotelback.dto.room.RoomAmenityDto;
import com.hjhotelback.dto.room.RoomDto;
import com.hjhotelback.dto.room.RoomDto_Client;
import com.hjhotelback.dto.room.RoomStatus;
import com.hjhotelback.dto.room.RoomTypeDto;
import com.hjhotelback.mapper.room.RoomRepository;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
//	======== 사용자 

	public List<RoomDto_Client> getRooms(){
		return roomRepository.AllRooms();
	}
	
//  ======== 관리	자
	public List<RoomDto> getAdminRooms(){
		return roomRepository.adminAllRooms();
	}
	
	public List<RoomDto> getRoomFloor(Integer floor){
		return roomRepository.adminFloorRoom(floor);
	}
	
	public List<RoomDto> getStatus(RoomStatus roomStatus){
		return roomRepository.allStatus(roomStatus);
	}
	
	public List<RoomDto> getAdminDetail(String roomNumber){
		return roomRepository.adminDetailRoom(roomNumber);
	}
	
	public ResponseEntity<String> UpdateStatus(RoomDto roomDto){
		return roomRepository.updateStatusRoom(roomDto);
	}
	
	public boolean deleteRoom(String roomNumber){
		int result = roomRepository.deleteRoom(roomNumber);
		return result > 0 ; // 24.11.22 진주 : 객실삭제 성공 여부반환
		
	}
// ======= 타입
	public List<RoomTypeDto> getTypes(){
		return roomRepository.allTypes();
	}
	
	public List<DetailRoomType> getTypesRoom(String roomType){
		return roomRepository.detailTypes(roomType);
	}
// ======= 어메니티 allAmenities
	public List<RoomAmenityDto> getAmenity(String TypeName){
		return roomRepository.typeAmenities(TypeName);
	}
	
	public boolean deleteAmenity(String name) {
		int result = roomRepository.deleteAmenity(name);
		return result > 0 ;
	}
//	public boolean updateAmenity(String roomTypeName,String amenity,boolean isActive) {
//		int rowUpdated = roomRepository.updateAmenityByType(roomTypeName,amenity,isActive);
//	return rowUpdated > 0;
	//업데이트 - 1로ㅅ 생성
//	}
	
	
}