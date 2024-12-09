package com.hjhotelback.controller.reservation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
        24.11.20 한택 [작업 일정] - 1주
        GET/api/reservation	
        POST/api/reservation
        추가 작업
        GET/api/reservation/admin
    */
    /*
        24.11.25 한택 [작업 일정] - 2주
        POST/api/reservation/update/{reservation_id} - 사용자 예약 날짜 변경
        POST/api/reservaion/admin/{reservation_id} - 관리자 예약 날짜 or 객실 or 상태 변경
        DELETE/api/reservation/cancel/{reservation_id} - 사용자 예약 취소
        작업하고 남은 일정 ResponseEntity로 변경 작업 하고, front 작업(리덕스) 할것
     */
    private final ReservationService _service;

    @PostMapping("/")
    @Transactional
    public ResponseEntity<String> POSTCreateReservation(@RequestBody ReqReservation.Create req){
        if (req.getCheckIn().compareTo(req.getCheckOut()) > 0) 
            return new ResponseEntity<String>("Error: Check-in date must be earlier than or equal to check-out date.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<String>(_service.createReservation(req),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResReservation.Detail GETReservationDetail(@RequestParam("id") int reservationId){
        return _service.getReservationDetail(reservationId);
    }

    //24.11.25 한택 [작업 정리] : 사용자 예약 날짜 변경 - 추가적인 변경 사항이 있을것 같아 POST 와 method 작명을 update로 그대로 사용하였음
    @PostMapping("update")
    public ResponseEntity<String> POSTUpdateReservationForUser(@RequestBody ReqReservation.UpdateDate req){

        return new ResponseEntity<>(_service.updateReservationForUser(req),HttpStatus.OK);
    }

    //24.11.20 한택 [주의] : status PENDING이면 삭제 , PENDING이 아닐 경우에는 status CANCELLED로 변경
    @DeleteMapping("cancel")
    public void DELETECancelReservation(@RequestBody ReqReservation.Delete req){
        _service.cancelReservation(req);
        
    }

    // -------------------------------------- Admin -------------------------------------------
    @GetMapping("admin")
    public List<ResReservation.Detail> GETAdminReservationList(){
        return _service.getReservationList();
    }

    @PostMapping("admin")
    public String POSTUpdateReservationForAdmin(@RequestBody ReqReservation.Update req){
        return _service.updateReservationForAdmin(req);
    }
    // -------------------------------------- Admin -------------------------------------------

    @GetMapping("summary/{selectedTimestamp}")
    public List<ResReservation.Summary> GETReservationSummary(@PathVariable("selectedTimestamp") long selectedTimestamp){
        return _service.getReservationSummary(selectedTimestamp);
    }

    @GetMapping("test/room")
    public List<ResReservation.RoomSample> GETRoomSample(){
        System.out.println("GETRoomSample method called");
        return _service.getRoomSample();
    }
}
