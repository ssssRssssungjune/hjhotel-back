package com.hjhotelback.dto.reservation;

import lombok.Getter;
import lombok.Setter;

public class ReservationDto {

    @Getter
    @Setter
    public static class GetList{
        private int lastValue;
        private String direction;
        private String colName;
        private String filterValue;        
        private int size;
    }
}
