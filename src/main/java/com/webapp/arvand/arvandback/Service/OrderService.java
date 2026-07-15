package com.webapp.arvand.arvandback.Service;

import com.webapp.arvand.arvandback.Entity.*;
import com.webapp.arvand.arvandback.AAProductService.ProductEntity;
import com.webapp.arvand.arvandback.Dto.*;
import com.webapp.arvand.arvandback.Repo.*;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    
    @Autowired
    private CartRepo cartRepo;
    
    @Autowired
    private CartService cartService;

    public OrderDto createOrder(UserEntity user, CreateOrderRequest request) throws ApiException {
        CartEntity cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "سبد خریدی یافت نشد"));

        if (cart.getItems().isEmpty()) {
            throw new ApiException(ApiErrorType.INVALID_REQUEST, "سبد خریدی خالی است");
        }

        if (request.getShippingAddress() == null || request.getShippingAddress().trim().isEmpty()) {
            throw new ApiException(ApiErrorType.INVALID_REQUEST, "آدرس تحویل الزامی است");
        }

        if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()) {
            throw new ApiException(ApiErrorType.INVALID_REQUEST, "شماره تماس الزامی است");
        }

        if (request.getRecipientName() == null || request.getRecipientName().trim().isEmpty()) {
            throw new ApiException(ApiErrorType.INVALID_REQUEST, "نام گیرنده الزامی است");
        }

        BigDecimal shippingCost = request.getShippingCost() != null ? request.getShippingCost() : BigDecimal.ZERO;
        BigDecimal discountAmount = request.getDiscountAmount() != null ? request.getDiscountAmount() : BigDecimal.ZERO;
        BigDecimal totalPrice = cart.getTotalPrice();
        BigDecimal finalPrice = totalPrice.add(shippingCost).subtract(discountAmount);

        List<OrderItemEntity> orderItems = cart.getItems().stream()
                .map(cartItem -> OrderItemEntity.builder()
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .unitPrice(cartItem.getUnitPrice())
                        .totalPrice(cartItem.getTotalPrice())
                        .variant(cartItem.getVariant())
                        .build())
                .collect(Collectors.toList());

        OrderEntity order = OrderEntity.builder()
                .user(user)
                .items(orderItems)
                .totalPrice(totalPrice)
                .shippingCost(shippingCost)
                .discountAmount(discountAmount)
                .finalPrice(finalPrice)
                .status(OrderEntity.OrderStatus.PENDING)
                .shippingAddress(request.getShippingAddress())
                .phoneNumber(request.getPhoneNumber())
                .recipientName(request.getRecipientName())
                .notes(request.getNotes())
                .build();

        OrderEntity savedOrder = orderRepo.save(order);
        
        for (OrderItemEntity item : orderItems) {
            item.setOrder(savedOrder);
        }
        savedOrder = orderRepo.save(savedOrder);

        cartService.clearCart(user);

        return mapToDto(savedOrder);
    }

    public OrderDto getOrder(UserEntity user, Long orderId) throws ApiException {
        OrderEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "سفارش یافت نشد"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ApiException(ApiErrorType.UNAUTHORIZED, "دسترسی غیر مجاز");
        }

        return mapToDto(order);
    }

    public Page<OrderDto> getUserOrders(UserEntity user, Pageable pageable) {
        return orderRepo.findByUser(user, pageable)
                .map(this::mapToDto);
    }

    public List<OrderDto> getUserOrdersAll(UserEntity user) {
        return orderRepo.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public OrderDto updateOrderStatus(Long orderId, OrderEntity.OrderStatus status) throws ApiException {
        OrderEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "سفارش یافت نشد"));
        
        order.setStatus(status);
        OrderEntity updatedOrder = orderRepo.save(order);
        return mapToDto(updatedOrder);
    }

    public void cancelOrder(UserEntity user, Long orderId) throws ApiException {
        OrderEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "سفارش یافت نشد"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ApiException(ApiErrorType.UNAUTHORIZED, "دسترسی غیر مجاز");
        }

        if (order.getStatus() != OrderEntity.OrderStatus.PENDING && 
            order.getStatus() != OrderEntity.OrderStatus.CONFIRMED) {
            throw new ApiException(ApiErrorType.INVALID_REQUEST, "این سفارش را نمی‌توان لغو کرد");
        }

        order.setStatus(OrderEntity.OrderStatus.CANCELLED);
        orderRepo.save(order);
    }

    private OrderDto mapToDto(OrderEntity order) {
        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(item -> OrderItemDto.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.getTotalPrice())
                        .variant(item.getVariant())
                        .build())
                .collect(Collectors.toList());

        return OrderDto.builder()
                .id(order.getId())
                .items(itemDtos)
                .totalPrice(order.getTotalPrice())
                .shippingCost(order.getShippingCost())
                .discountAmount(order.getDiscountAmount())
                .finalPrice(order.getFinalPrice())
                .status(order.getStatus().name())
                .shippingAddress(order.getShippingAddress())
                .phoneNumber(order.getPhoneNumber())
                .recipientName(order.getRecipientName())
                .notes(order.getNotes())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
