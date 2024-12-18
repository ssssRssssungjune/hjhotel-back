package com.hjhotelback.controller.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.room.TypeDetailDto_Client;
import com.hjhotelback.dto.room.CountTypeDto;
import com.hjhotelback.dto.room.RoomTypeDto_Client;
import com.hjhotelback.service.room.RoomService;

@RestController
@RequestMapping("/api/users/rooms")
public class ClientRoomController {
	
	@Autowired RoomService roomService;
	
	// 24.11.27 진주 : 사용자 - 객실 타입별 조회
	@GetMapping("")
	public List<RoomTypeDto_Client> getTypes(){
		return roomService.getRoomTypes();
				
	}
	//24.11.27 진주 : 사용자 - 객실 타입 상세 조회 - 스탠다드,디럭스
	@GetMapping("/detail/{roomTypeName}")
	public List<TypeDetailDto_Client> getDetailType(@PathVariable("roomTypeName")String roomTypeName){
		
		return roomService.getDetailType(roomTypeName);
				
	}
	
	// 24.12.12 진주 : 사용자 - 타입count조회
			@GetMapping("/types/count")
			public List<CountTypeDto> getcountTypes(){
				return roomService.getcountTypes();
			}
	
	
}
