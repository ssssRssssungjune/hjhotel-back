package com.hjhotelback.dto.payment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessDTO {
    private Integer processId;
    private Integer reservationId;
    private String provider;
    private String sessionId;
    private PaymentProcessStatus processStatus; // ENUM으로 처리
    private String redirectUrl;
    private LocalDateTime createdAt;
}
