package com.hjhotelback.controller.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.room.RoomAmenityDto;
import com.hjhotelback.service.room.RoomService;

@RestController
@RequestMapping("/api/admin/amenity")
public class RoomAmenityController {
	@Autowired RoomService roomService;
	
	@GetMapping("")
	public List<RoomAmenityDto> getAmenities(){
		return roomService.getAmenities();
	}
}
