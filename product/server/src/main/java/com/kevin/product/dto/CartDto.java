package com.kevin.product.dto;

import lombok.Data;

@Data
public class CartDto {
    private String productId;
    private Integer productQuantity;
}
