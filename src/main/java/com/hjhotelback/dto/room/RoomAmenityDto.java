package com.hjhotelback.dto.room;

import lombok.Data;

@Data
public class RoomAmenityDto {

    private String roomTypeName;         // 어메니티 고유 숫자
    private String amenityName;    // 객실별 어메니티
    private String amenityDesciption;               // 어메니티 설명
    private Boolean amenityActive;      // 어메니티 활성화
}
