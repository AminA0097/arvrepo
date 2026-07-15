package com.webapp.arvand.arvandback.Controller;

import com.webapp.arvand.arvandback.Dto.CreateOrderRequest;
import com.webapp.arvand.arvandback.Dto.OrderDto;
import com.webapp.arvand.arvandback.Service.OrderService;
import com.webapp.arvand.arvandback.Utills.ApiResponse;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Security.Auth.UserDetail;
import com.webapp.arvand.arvandback.Entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(
            Authentication authentication,
            @RequestBody CreateOrderRequest request) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        OrderDto order = orderService.createOrder(userDetail.getUser(), request);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrder(
            Authentication authentication,
            @PathVariable Long orderId) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        OrderDto order = orderService.getOrder(userDetail.getUser(), orderId);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getUserOrders(
            Authentication authentication,
            Pageable pageable) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        Page<OrderDto> orders = orderService.getUserOrders(userDetail.getUser(), pageable);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getUserOrdersAll(
            Authentication authentication) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        List<OrderDto> orders = orderService.getUserOrdersAll(userDetail.getUser());
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> cancelOrder(
            Authentication authentication,
            @PathVariable Long orderId) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        orderService.cancelOrder(userDetail.getUser(), orderId);
        return ResponseEntity.ok(ApiResponse.success("سفارش لغو شد"));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) throws ApiException {
        
        try {
            OrderEntity.OrderStatus orderStatus = OrderEntity.OrderStatus.valueOf(status);
            OrderDto order = orderService.updateOrderStatus(orderId, orderStatus);
            return ResponseEntity.ok(ApiResponse.success(order));
        } catch (IllegalArgumentException e) {
            throw new ApiException(null, "وضعیت سفارش نامعتبر است");
        }
    }
}
