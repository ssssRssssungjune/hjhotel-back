package com.hjhotelback.controller.room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.hjhotelback.dto.room.RoomAmenityDto;
import com.hjhotelback.dto.room.TypeDetailDto_Client;
import com.hjhotelback.dto.room.RoomDto;

import com.hjhotelback.dto.room.RoomStatus;
import com.hjhotelback.dto.room.RoomTypeDto;
import com.hjhotelback.service.room.RoomService;


@RestController
@RequestMapping("/api/admin/rooms")
public class RoomController {
	@Autowired
	private RoomService roomService;

	// 24.11.27 진주 : 관리자- 날짜,상태로 객실 리스트 불러오기
	
	@GetMapping("")
	public List<RoomDto> getAdminRooms(@RequestParam("date") @DateTimeFormat(iso =DateTimeFormat.ISO.DATE_TIME) LocalDate date,@RequestParam("resStatus")String resStatus){
		return roomService.getAdminRooms(date,resStatus);	
		}
//	@GetMapping
//	public String test(
//			@RequestParam("date")String date,@RequestParam("resStatus")String resStatus) {
//		return "test"+ date + resStatus;
//	}
	
	// 24.11.27 진주 : 관리자- 날짜로 객실 리스트 불러오기
	
	@GetMapping("/{date}")
	public List<RoomDto> getAllDateRooms(@PathVariable("date")LocalDateTime date){
		return roomService.getAllDateRooms(date);	
		}
	
	
	//24.11.22 진주 : 관리자- 객실 상태 변경
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
	// 24.11.25 진주 : 관리자- 타입조회
	@GetMapping("/types")
	public List<RoomTypeDto> getTypes(){
		return roomService.getTypes();
	}
	
	// 24.11.25 진주 : 관리자- 타입별 상세(+해당 타입 어메니티) 조회 - 
		@GetMapping("/types/{TypeName}")
		public List<RoomAmenityDto> getTypesRoom(@PathVariable("TypeName")String TypeName){
			return roomService.getAmenity(TypeName);
		}
	// 24.11.27 진주 : 관리자 - amenity 항목 사용여부(isActive)변경
//		@PutMapping("/{roomTypeName}/{amenity}")
//		public RequestEntity<String> updateAmenities(@PathVariable("roomTypeName") String roomTypeName,@PathVariable("amenity") String amenity,@RequestBody boolean isActive){
//			
//			boolean isUpdated = roomService.updateAmenity(roomTypeName,amenity,isActive);
//			
//			if(isUpdated) {
//				return ResponseEntity.ok("Amenity status updated successfully.");
//			}else {
//				return ResponseEntity.notFound().body("Room type or amenity not found");
//			}
//		}
//	
	

	
	
	

	
	
}