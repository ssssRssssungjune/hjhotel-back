package com.hjhotelback.controller.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.room.DetailRoomType;
import com.hjhotelback.dto.room.RoomDto;

import com.hjhotelback.dto.room.RoomStatus;
import com.hjhotelback.dto.room.RoomTypeDto;
import com.hjhotelback.service.room.RoomService;


@RestController
@RequestMapping("/api/admin/rooms")
public class RoomController {
	@Autowired
	private RoomService roomService;

	// 24.11.21 진주 : 관리자- 리스트 불러오기
	
	@GetMapping("")
	public List<RoomDto> getAdminRooms(){
		return roomService.getAdminRooms();	
		}
	
	//24.11.22 진주: 관리자- 층별 객실 조회
		@GetMapping("/floor/{floor}")
		public List<RoomDto> getFloor(@PathVariable("floor") Integer floor){
			return roomService.getRoomFloor(floor);
		}
	
	//24.11.22 진주: 관리자- 상태조회
	@GetMapping("/status/{status}")
	public List<RoomDto> getStatus(@PathVariable("status") RoomStatus roomStatus){
		return roomService.getStatus(roomStatus);
	}
	
	// 24.11.22 진주 : 관리자- 객실 상세 조회 
	@GetMapping("/details/{roomNumber}")
	public List<RoomDto> getAdminDetail(@PathVariable("roomNumber") String roomNumber){
		return roomService.getAdminDetail(roomNumber);	}
	
	
	//24.11.22 진주 : 관리자- 객실 상세 상태 변경
	@PutMapping("/details/{roomNumber}/{status}")
	public ResponseEntity<String> updateStatus(@PathVariable("roomNumber") String roomNumber,@PathVariable("status") String status){
		RoomDto roomDto = new RoomDto();
		roomDto.setRoomNumber(roomNumber);
		// 24.11.22 진주 : 객실상태 enum-> string 
	try {
		RoomStatus roomStatus = RoomStatus.valueOf(status.toUpperCase());
		roomDto.setStatus(roomStatus);       
		  roomService.UpdateStatus(roomDto);
		 return ResponseEntity.ok().build();           

	}catch(IllegalArgumentException e) {
		// 24.11.22 진주 : 객실상태 (enum) 대문자로 받기 예외처리 : valueof에서 예외 발생, status가 유효하지 않
		return ResponseEntity.badRequest().body("Invalid room status:"+ status);
	}
	}
	// 24.11.23 진주 :관리자- 객실 삭제
	@DeleteMapping("/{roomNumber}")
	public ResponseEntity<Void> deleteRoom(@PathVariable("roomNumber") String roomNumber){
		boolean isDeleted = roomService.deleteRoom(roomNumber);
		if(isDeleted) {
			// 24.11.23 진주 : 객실삭제 성공시 204 No contetnt 반환
			return ResponseEntity.noContent().build();
		}else {
			// 24.11.23 진주 : 객실삭제 성공시 404 Not Found 반환
			return ResponseEntity.notFound().build();
		}
		
	}
	// 24.11.25 진주 : 관리자- 타입조회
	@GetMapping("/types")
	public List<RoomTypeDto> getTypes(){
		return roomService.getTypes();
	}
	
	// 24.11.25 진주 : 관리자- 타입별 객실 조회 
		@GetMapping("/types/{roomType}")
		public List<DetailRoomType> getTypesRoom(@PathVariable("roomType")String roomType){
			return roomService.getTypesRoom(roomType);
		}
	

	
	
	

	
	
}