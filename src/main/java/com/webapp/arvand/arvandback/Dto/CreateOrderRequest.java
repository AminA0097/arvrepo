package com.webapp.arvand.arvandback.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private BigDecimal shippingCost;
    private BigDecimal discountAmount;
    private String shippingAddress;
    private String phoneNumber;
    private String recipientName;
    private String notes;
}
