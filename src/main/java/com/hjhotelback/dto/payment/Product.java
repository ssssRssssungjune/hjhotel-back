package com.hjhotelback.dto.payment;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
}
