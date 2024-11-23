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
}
