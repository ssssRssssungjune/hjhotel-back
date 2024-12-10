package com.hjhotelback.service.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjhotelback.dto.room.TypeDetailDto_Client;
import com.hjhotelback.dto.room.CountTypeDto;
import com.hjhotelback.dto.room.RoomAmenityDto;
import com.hjhotelback.dto.room.RoomDto;
import com.hjhotelback.dto.room.RoomTypeDto_Client;
import com.hjhotelback.dto.room.RoomTypeDto;
import com.hjhotelback.mapper.room.RoomRepository;

@Service
public class RoomService {

	
	@Autowired
	private RoomRepository roomRepository;
//	======== 사용자 

	public List<RoomTypeDto_Client> getRoomTypes(Integer typeId){

		return roomRepository.AllRoomTypes(typeId);
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
	public List<RoomDto> getAllDateRooms(LocalDate date){
		return roomRepository.AllRoomsDate(date);
	}
	
	// 24.11.27 진주 : 관리자- 객실 상태 변경
	public int UpdateStatus(RoomDto roomDto){
		
		return roomRepository.updateStatusRoom(roomDto);
	}

// ======= 타입
	public List<RoomTypeDto> getTypes(){
		return roomRepository.allTypes();
	}
	
	// ======= 타입 - count
		public List<CountTypeDto> getcountTypes(){
			return roomRepository.countTypes();
		}
	
// ======= 타입별 amenity 조회
	public List<RoomAmenityDto> getAmenity(String TypeName){
		List<RoomAmenityDto> amenity = roomRepository.typeAmenities(TypeName);
		return amenity;
	}

// ======= 타입 별 amenity 활성화
	public int toggleAmenity(String TypeName,String amenity) {
		return roomRepository.updateAmenity(TypeName,amenity);
		//return updateRows >0;
	}

	
}