package com.webapp.arvand.arvandback.Controller;

import com.webapp.arvand.arvandback.Dto.CartDto;
import com.webapp.arvand.arvandback.Service.CartService;
import com.webapp.arvand.arvandback.Utills.ApiResponse;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Security.Auth.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse> getCart(Authentication authentication) throws ApiException {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        CartDto cart = cartService.getCart(userDetail.getUser());
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(
            Authentication authentication,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            @RequestParam(required = false) String variant) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        CartDto cart = cartService.addToCart(userDetail.getUser(), productId, quantity, variant);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<ApiResponse> removeFromCart(
            Authentication authentication,
            @PathVariable Long cartItemId) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        CartDto cart = cartService.removeFromCart(userDetail.getUser(), cartItemId);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        CartDto cart = cartService.updateCartItem(userDetail.getUser(), cartItemId, quantity);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse> clearCart(Authentication authentication) throws ApiException {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        cartService.clearCart(userDetail.getUser());
        return ResponseEntity.ok(ApiResponse.success("سبد خریدی خالی شد"));
    }
}
