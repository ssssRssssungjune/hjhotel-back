package com.hjhotelback.controller.reservation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjhotelback.dto.reservation.ReqReservation;
import com.hjhotelback.dto.reservation.ResReservation;
import com.hjhotelback.service.reservation.ReservationService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    // 24.11.20 한택 [기능 정리]
    // 에약 목록 보기 사용자/관리자 
    // 예약 상세 보기 사용자/관리자     
    // 예약 생성 사용자만
    // 예약 수정 사용자/관리자 - 사용자 : {예약대기(결제전) -> 예약완료(결제후), 예약 취소}예약상태변경, 예약날짜변경 / 
    //                         관리자 : 예약날짜 변경 , 객실 변경 , 예약 취소(PENDING 상태일때만)
    // 예약 취소는 API 분리하여 작업할 것

    /*
        24.11.20 한택 [작업 일정]
        1주차
        GET/api/reservation	
        POST/api/reservation	
    */
    private final ReservationService _service;

    @PostMapping("/")
    public void POSTCreateReservation(@RequestBody ReqReservation.Create req){
        _service.createReservation(req);
    }

    @GetMapping("/")
    public ResReservation.Detail GETReservationDetail(@RequestParam int id){
        return _service.getReservationDetail(id);
    }

    @GetMapping("/admin")
    public void GETReservationList(@RequestParam int id){
        
    }

    @PutMapping("{reservation_id}/modify")
    public void PUTUpdateReservation(@RequestBody ReqReservation.Modify dto){

    }

    //24.11.20 한택 [주의점] : status PENDING이면 삭제 , PENDING이 아닐 경우에는 status CANCELLED로 변경
    @DeleteMapping("{reservation_id}/cancel")
    public void DELETECancelReservation(){

    }

}
