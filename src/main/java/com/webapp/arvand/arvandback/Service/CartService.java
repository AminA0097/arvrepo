package com.webapp.arvand.arvandback.Service;

import com.webapp.arvand.arvandback.Entity.CartEntity;
import com.webapp.arvand.arvandback.Entity.CartItemEntity;
import com.webapp.arvand.arvandback.Entity.UserEntity;
import com.webapp.arvand.arvandback.AAProductService.ProductEntity;
import com.webapp.arvand.arvandback.AAProductService.ProductRepo;
import com.webapp.arvand.arvandback.Dto.CartDto;
import com.webapp.arvand.arvandback.Dto.CartItemDto;
import com.webapp.arvand.arvandback.Repo.CartRepo;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartRepo cartRepo;
    
    @Autowired
    private ProductRepo productRepo;

    public CartDto getCart(UserEntity user) throws ApiException {
        CartEntity cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "سبد خریدی یافت نشد"));
        return mapToDto(cart);
    }

    public CartDto addToCart(UserEntity user, Long productId, Integer quantity, String variant) throws ApiException {
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "محصول یافت نشد"));

        CartEntity cart = cartRepo.findByUser(user).orElseGet(() -> {
            CartEntity newCart = CartEntity.builder()
                    .user(user)
                    .totalPrice(BigDecimal.ZERO)
                    .itemCount(0)
                    .build();
            return cartRepo.save(newCart);
        });

        CartItemEntity existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId) && 
                        (variant == null || variant.equals(item.getVariant())))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setTotalPrice(existingItem.getUnitPrice().multiply(new BigDecimal(existingItem.getQuantity())));
        } else {
            CartItemEntity newItem = CartItemEntity.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .unitPrice(product.getDiscountedPrice() != null ? product.getDiscountedPrice() : product.getPrice())
                    .totalPrice(new BigDecimal(quantity).multiply(
                            product.getDiscountedPrice() != null ? product.getDiscountedPrice() : product.getPrice()))
                    .variant(variant)
                    .build();
            cart.getItems().add(newItem);
        }

        updateCartTotals(cart);
        cartRepo.save(cart);
        return mapToDto(cart);
    }

    public CartDto removeFromCart(UserEntity user, Long cartItemId) throws ApiException {
        CartEntity cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "سبد خریدی یافت نشد"));

        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        updateCartTotals(cart);
        cartRepo.save(cart);
        return mapToDto(cart);
    }

    public CartDto updateCartItem(UserEntity user, Long cartItemId, Integer quantity) throws ApiException {
        CartEntity cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "سبد خریدی یافت نشد"));

        CartItemEntity item = cart.getItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "محصول در سبد یافت نشد"));

        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(quantity);
            item.setTotalPrice(item.getUnitPrice().multiply(new BigDecimal(quantity)));
        }

        updateCartTotals(cart);
        cartRepo.save(cart);
        return mapToDto(cart);
    }

    public void clearCart(UserEntity user) throws ApiException {
        CartEntity cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "سبد خریدی یافت نشد"));
        cart.getItems().clear();
        updateCartTotals(cart);
        cartRepo.save(cart);
    }

    private void updateCartTotals(CartEntity cart) {
        BigDecimal total = cart.getItems().stream()
                .map(CartItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int count = cart.getItems().stream()
                .mapToInt(CartItemEntity::getQuantity)
                .sum();
        
        cart.setTotalPrice(total);
        cart.setItemCount(count);
    }

    private CartDto mapToDto(CartEntity cart) {
        List<CartItemDto> itemDtos = cart.getItems().stream()
                .map(item -> CartItemDto.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .productImage(item.getProduct().getImage())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.getTotalPrice())
                        .variant(item.getVariant())
                        .build())
                .collect(Collectors.toList());

        return CartDto.builder()
                .id(cart.getId())
                .items(itemDtos)
                .totalPrice(cart.getTotalPrice())
                .itemCount(cart.getItemCount())
                .build();
    }
}
