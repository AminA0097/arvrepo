package com.webapp.arvand.arvandback.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    private BigDecimal shippingCost;
    private BigDecimal discountAmount;
    private BigDecimal finalPrice;
    private String status;
    private String shippingAddress;
    private String phoneNumber;
    private String recipientName;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
