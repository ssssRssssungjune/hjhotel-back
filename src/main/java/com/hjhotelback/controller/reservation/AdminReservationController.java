package com.hjhotelback.controller.reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.reservation.ReqReservation;
import com.hjhotelback.dto.reservation.ResReservation;
import com.hjhotelback.service.reservation.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminReservationController {

    private final ReservationService _service;

     @GetMapping("reservation")
    public ResReservation.Detail GETReservationDetail(@RequestParam("id") int reservationId){
        return _service.getReservationDetail(reservationId);
    }

    @DeleteMapping("reservation/cancel")
    public void DELETECancelReservation(@RequestBody ReqReservation.Delete req){
        _service.cancelReservation(req);
        
    }

    @PostMapping("update")
    public String POSTUpdateReservationForAdmin(@RequestBody ReqReservation.Update req){
        return _service.updateReservationForAdmin(req);
    }

    @GetMapping("reservation/list/{direction:next|prev}")
    public List<ResReservation.Detail> GETAdminReservationList(
        @RequestParam(value ="last_value",required = false) int lastValue,
        @PathVariable("direction") String direction,
        @RequestParam("col") String colName,
        @RequestParam("filter") String filterValue,
		@RequestParam("size") int size
    ){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("lastValue",lastValue);
        param.put("direction",direction);
        param.put("colName",colName);
        param.put("filterValue",filterValue);
        param.put("size",size);
        
        return _service.getReservationList(param);
    }

}
