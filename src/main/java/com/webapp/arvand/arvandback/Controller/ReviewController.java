package com.webapp.arvand.arvandback.Controller;

import com.webapp.arvand.arvandback.Dto.CreateReviewRequest;
import com.webapp.arvand.arvandback.Dto.ReviewDto;
import com.webapp.arvand.arvandback.Service.ReviewService;
import com.webapp.arvand.arvandback.Utills.ApiResponse;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Security.Auth.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse> createReview(
            Authentication authentication,
            @RequestBody CreateReviewRequest request) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        ReviewDto review = reviewService.createReview(userDetail.getUser(), request);
        return ResponseEntity.ok(ApiResponse.success(review));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> getProductReviews(
            @PathVariable Long productId,
            Pageable pageable) throws ApiException {
        
        Page<ReviewDto> reviews = reviewService.getProductReviews(productId, pageable);
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> updateReview(
            Authentication authentication,
            @PathVariable Long reviewId,
            @RequestBody CreateReviewRequest request) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        ReviewDto review = reviewService.updateReview(userDetail.getUser(), reviewId, request);
        return ResponseEntity.ok(ApiResponse.success(review));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            Authentication authentication,
            @PathVariable Long reviewId) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        reviewService.deleteReview(userDetail.getUser(), reviewId);
        return ResponseEntity.ok(ApiResponse.success("نظر حذف شد"));
    }

    @GetMapping("/product/{productId}/rating")
    public ResponseEntity<ApiResponse> getProductAverageRating(
            @PathVariable Long productId) throws ApiException {
        
        Double rating = reviewService.getProductAverageRating(productId);
        return ResponseEntity.ok(ApiResponse.success(rating));
    }

    @GetMapping("/product/{productId}/count")
    public ResponseEntity<ApiResponse> getProductReviewCount(
            @PathVariable Long productId) throws ApiException {
        
        Long count = reviewService.getProductReviewCount(productId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUserReviews(
            Authentication authentication) throws ApiException {
        
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        List<ReviewDto> reviews = reviewService.getUserReviews(userDetail.getUser());
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }
}
