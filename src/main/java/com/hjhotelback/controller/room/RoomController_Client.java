package com.hjhotelback.controller.room;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.room.RoomDto;
import com.hjhotelback.dto.room.RoomDto_Client;
import com.hjhotelback.service.room.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController_Client {
	
	@Autowired RoomService roomService;
	
	@GetMapping("")
	public List<RoomDto> getRooms(){
		
		return roomService.getRooms()
				.stream()
				.map(room -> new RoomDto(room.getRoomTypeId(),room.getRoomNumber(),room.getFloor(),room.getView()))
				.collect(Collectors.toList());
				
	}
	
}
