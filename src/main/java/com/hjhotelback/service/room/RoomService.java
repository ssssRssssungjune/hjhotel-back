package com.hjhotelback.service.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.hjhotelback.dto.room.TypeDetailDto_Client;
import com.hjhotelback.dto.room.AddAmenityDto;
import com.hjhotelback.dto.room.CountTypeDto;
import com.hjhotelback.dto.room.RoomAddDto;
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
	public List<RoomDto> getAllDateRooms(LocalDate date){
		return roomRepository.AllRoomsDate(date);
	}
	
	// 24.11.27 진주 : 관리자- 객실 상태 변경
	public int UpdateStatus(RoomDto roomDto){
		
		return roomRepository.updateStatusRoom(roomDto);
	}
	
	// 24.12.10. 진주 : 관리자 - 객실 추가
	//	@Transactional
		//public void addRoom(RoomAddDto roomAddDto) {
			//room_id의 최대
			//int maxRoomId = roomRepository.getMaxRoomId();
			//Auto_INCREMENT 값 설정
			//roomRepository.setAutoIncrement(maxRoomId+1);
			//room 데이터
			//roomRepository.insertRoom(roomAddDto);
		//}

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
	// ======= amnenity 추가
	public void addAmenity(AddAmenityDto addAmenityDto) {
		// TODO Auto-generated method stub
		roomRepository.addAmenity(addAmenityDto);
	}

	
	
}