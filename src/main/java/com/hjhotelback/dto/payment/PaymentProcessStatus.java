package com.hjhotelback.dto.payment;

public enum PaymentProcessStatus {
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    private final String status;

    PaymentProcessStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
    // String으로 enum 값을 매핑하는 유틸리티 메서드
    public static PaymentProcessStatus fromString(String status) {
        for (PaymentProcessStatus PaymentProcessStatus : PaymentProcessStatus.values()) {
            if (PaymentProcessStatus.status.equalsIgnoreCase(status)) {
                return PaymentProcessStatus;
            }
        }
        throw new IllegalArgumentException("Unknown enum status: " + status);
    }
}
