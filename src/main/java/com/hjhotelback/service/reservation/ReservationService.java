package com.hjhotelback.service.reservation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hjhotelback.dto.reservation.ReqReservation;
import com.hjhotelback.dto.reservation.ResReservation;
import com.hjhotelback.dto.reservation.ReservationDTO;
import com.hjhotelback.mapper.reservation.ReservationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    /*
        24.11.20 한택 [작업 일정]
        1주차
        GET/api/reservation	
        POST/api/reservation	
    */

    private final ReservationMapper _mapper;

    //24.11.21 한택 [변수이름]
    //sql colName / member_id, room_id, check_in, check_out, status, total_amount
    //java varName ReqReservation.Create.~ / memberId, roomId, checkIn, checkOut, totalAmount
    //24.11.21 한택 [예정 작업] : 유효성 검사 필요 why 안정성을 위해서 / 추가 의문 - 안정성 검사를 서비스 계층까지 와서 작업해야되나
    public String createReservation(ReqReservation.Create req){
        // 24.11.24 한택 [작업 정리] : 유효성 검사에 대한 정리 내용 
        // 사용자 고유 id , 객실 고유 id , 예약하려는 기간(checkin , checkout) , 총금액(객실 1인 숙박비용 * 기간)
        // 프론트에서도 유효성검사를 하지만 안정성을 위해 검사하야될 것 
        //  1.  해당 객실이 기간에 사용이 가능한지 - db에서 검사
        //  2.  checin < checkout - 비즈니스 로직에서 처리(백엔드 처리)
        int rowsInserted = _mapper.createReservation(req);

        if (rowsInserted > 0) {
            return "Reservation created successfully!";
        } else {
            return "Failed to create reservation. The room might be unavailable.";
        }
    }

    //24.11.21 한택 [보류] : 사용자와 관리자 조회를 따로 만들지 생각해봐야 함 / 현재는 기능 동일
    public ResReservation.Detail getReservationDetail(int reservationId){
        return _mapper.getReservationById(reservationId);
    }

    public List<ResReservation.Detail> getReservationList(Map<String,Object> param){
        return _mapper.getReservationList(param);
    }

    // 예약 수정 사용자/관리자 - 사용자 : {예약대기(결제전) -> 예약완료(결제후), 예약 취소}예약상태변경, 예약날짜변경 / 
    //                         관리자 : 예약날짜 변경 , 객실 변경 , 예약 취소(PENDING 상태일때만)
    public String updateReservationForUser(ReqReservation.UpdateDate req){
        //24.11.26 한택 [작업 정리] : 
        // public class UpdateDate implements Update{
        //     public int reservationId;
        //     public Timestamp checkIn;
        //     public Timestamp checkOut;
        // }
        if (req.checkIn.compareTo(req.checkOut) > 0)
            return null;

        updateReservationDate(req);
        

        return null;
    }

    public void cancelReservation(ReqReservation.Delete req){
        _mapper.deleteReservation(req);
    }
    
    //24.11.27 한택 [보류] : 분기 처리 좀 더 고민해볼 것
    // 1. command 패턴
    // 2. DTO에서 일부 로직 처리
    public String updateReservationForAdmin(ReqReservation.Update req){
        if(req instanceof ReqReservation.UpdateDate)
            return updateReservationDate((ReqReservation.UpdateDate)req) ? "Date updated" : "Update failed";
        else if(req instanceof ReqReservation.UpdateState)
            return updateReservationState((ReqReservation.UpdateState)req) ? "State updated" : "Update failed";
        else if(req instanceof ReqReservation.UpdateRoom)
            return updateReservationRoom((ReqReservation.UpdateRoom)req) ? "Room updated" : "Update failed";
        else
            return "Invalid request type";
    }

    // 24.12.09 한택 [내용] : 선택한 현재 월의 예약가능객실 조회
    public List<ResReservation.Summary> getReservationSummary(long selectedTimestamp){
        return _mapper.getRoomReservationSummary(convertTimestampToDate(selectedTimestamp));
    }

    //24.12.01 한택 [내용] :
    public boolean UpdateReservationState(ReqReservation.UpdateState data){

        return updateReservationState(data);
    }

    // 예약 날짜 변경 로직
    private boolean updateReservationDate(ReqReservation.UpdateDate data){
        if (data.checkIn.compareTo(data.checkOut) > 0)
            return false;

        return _mapper.updateReservationDate(data) > 0;
    }

    // 예약 상태 변경 로직
    private boolean updateReservationState(ReqReservation.UpdateState data){
        
        return _mapper.updateReservationState(data) > 0;

    }

    // 예약 객실 변경 로직
    private boolean updateReservationRoom(ReqReservation.UpdateRoom data){

        return _mapper.updateReservationRoom(data) > 0;
    }

    // util
    private LocalDate convertTimestampToDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .toLocalDate()
                    .withDayOfMonth(1);
    }
    
    // test
    public List<ResReservation.RoomSample> getRoomSample(){
        return _mapper.getRoomSample();
    }
}

