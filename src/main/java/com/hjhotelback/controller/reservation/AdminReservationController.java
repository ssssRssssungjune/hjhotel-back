package com.hjhotelback.controller.reservation;

import java.util.HashMap;
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
@RequestMapping("/api/admin/reservation")
@RequiredArgsConstructor
public class AdminReservationController {

    private final ReservationService _service;

     @GetMapping
    public ResReservation.Detail GETReservationDetail(@RequestParam("id") int reservationId){
        return _service.getReservationDetail(reservationId);
    }

    @DeleteMapping
    public void DELETECancelReservation(@RequestBody ReqReservation.Delete req){
        _service.cancelReservation(req);
        
    }

    @PostMapping
    public String POSTUpdateReservationForAdmin(@RequestBody ReqReservation.Update req){
        return _service.updateReservationForAdmin(req);
    }

    @GetMapping(value = {"list/{direction}", "list"})
    public ResReservation.GetList GETAdminReservationList(
		@RequestParam(value = "size", required = true) Integer size,
        @RequestParam(value = "lastValue", required = false) Integer lastValue,
        @PathVariable(value = "direction", required = false) String direction,
        @RequestParam(value = "colName", required = false) String colName,
        @RequestParam(value = "filter", required = false) String filterValue
    ){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("size",size);
        if(lastValue != null) param.put("lastValue",lastValue);
        if(direction != null) param.put("direction",direction);
        if(colName != null) param.put("colName",colName);
        if(filterValue != null) param.put("filterValue",filterValue);
        
        ResReservation.GetList resData = _service.getReservationList(param);

        return resData;
    }

}
