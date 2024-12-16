package com.hjhotelback.controller.room;

import java.io.Console;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.room.CountTypeDto;
import com.hjhotelback.dto.room.RoomAddDto;
import com.hjhotelback.dto.room.RoomAmenityDto;
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
	
	@GetMapping()
	public List<RoomDto> getAdminRooms(@RequestParam("date") @DateTimeFormat(iso =DateTimeFormat.ISO.DATE_TIME) LocalDate date,@RequestParam("resStatus")String resStatus){
		return roomService.getAdminRooms(date,resStatus);	
		}
	
	// 24.11.27 진주 : 관리자- 날짜로 객실 리스트 불러오기
	@GetMapping("/roomdetails")
	public List<RoomDto> getAllDateRooms(@RequestParam("date") @DateTimeFormat(iso =DateTimeFormat.ISO.DATE_TIME) LocalDate date){
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
			return ResponseEntity.ok("객실상태 변경 완료");           
	
		}catch(IllegalArgumentException e) {
			// 24.11.22 진주 : 객실상태 (enum) 대문자로 받기 예외처리 : valueof에서 예외 발생, status가 유효하지 않
			return ResponseEntity.badRequest().body("잘못된 입력 :"+ status);
		}
	}
	
//	// 24.12.10 진주 : 관리자 객실 추가
//			@PostMapping("/add")
//			public ResponseEntity<String> addRoom(@RequestBody RoomAddDto roomAddDto){
//				try {
//					
//					roomService.addRoom(roomAddDto);
//					return ResponseEntity.ok("객실이 추가되었어요!");
//				}catch(Exception e) {
//					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("객실 추가를 실패했어요!");
//				}
//				
//			
//			}
			
	
	// 24.11.25 진주 : 관리자- 타입조회
	@GetMapping("/types")
	public List<RoomTypeDto> getTypes(){
		return roomService.getTypes();
	}
	
	
	// 24.11.25 진주 : 관리자- 타입count조회
		@GetMapping("/types/count")
		public List<CountTypeDto> getcountTypes(){
			return roomService.getcountTypes();
		}
	
	
	
	// 24.11.25 진주 : 관리자- 타입별 상세(+해당 타입 어메니티 수정) 조회 -  스탠다드 
		@GetMapping("/types/{TypeName}")
		public List<RoomAmenityDto> getTypesRoom(@PathVariable("TypeName")String TypeName){
			return roomService.getAmenity(TypeName);
		}
		
	
//	24.11.28 진주 : 관리자 - amenity isActive 활성화
		@PutMapping("/toggle")
		public ResponseEntity<String> toggleAmenity(@RequestParam("TypeName") String TypeName,@RequestParam("amenity") String amenity){
			
			int isUpdated = roomService.toggleAmenity(TypeName,amenity);
			
			if(isUpdated > 0) {
				return ResponseEntity.ok(amenity+ " 활성화 변경 완료!");
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(amenity+"변경 실패");
			}
		}
	

	
	
	

	
	
}