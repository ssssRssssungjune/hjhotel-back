package com.hjhotelback.dto.payment;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentProcessLogDTO {
    private LocalDateTime createdAt;
    private String status;
}
