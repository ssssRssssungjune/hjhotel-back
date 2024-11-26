package com.hjhotelback.controller.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.room.RoomAmenityDto;
import com.hjhotelback.service.room.RoomService;

@RestController
@RequestMapping("/api/admin/amenity")
public class RoomAmenityController {
	@Autowired RoomService roomService;
	// 24.11.25 진주 : 타입별 amenity 목록 조회
	
	@GetMapping("/{TypeName}")
	public List<RoomAmenityDto> getAmenities(@PathVariable("TypeName")String TypeName){
		return roomService.getAmenity(TypeName);
	}

	
	// 24.11.26 진주 : 객실별 amenity 삭제
	@DeleteMapping("/{name}")
	public ResponseEntity<Void> deleteAmenity(@PathVariable("name")String name){
			boolean isDelete = roomService.deleteAmenity(name);
			if(isDelete) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.notFound().build();
			}
		 
		
	}
	
	 //24.11.26 진주 : 객실별 amenity 항목 사용여부(isActive)변경
//	@PutMapping("/{roomTypeName}/{amenity}")
//	public RequestEntity<String> updateAmenities(@PathVariable("roomTypeName") String roomTypeName,@PathVariable("amenity") String amenity,@RequestBody boolean isActive){
//		
//		boolean isUpdated = roomService.updateAmenity(roomTypeName,amenity,isActive);
//		
//		if(isUpdated) {
//			return ResponseEntity.ok("Amenity status updated successfully.");
//		}else {
//			return ResponseEntity.notFound().body("Room type or amenity not found");
//		}
//	}

	
}
