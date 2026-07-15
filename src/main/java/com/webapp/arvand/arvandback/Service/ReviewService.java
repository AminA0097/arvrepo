package com.webapp.arvand.arvandback.Service;

import com.webapp.arvand.arvandback.Entity.ReviewEntity;
import com.webapp.arvand.arvandback.Entity.UserEntity;
import com.webapp.arvand.arvandback.AAProductService.ProductEntity;
import com.webapp.arvand.arvandback.AAProductService.ProductRepo;
import com.webapp.arvand.arvandback.Dto.ReviewDto;
import com.webapp.arvand.arvandback.Dto.CreateReviewRequest;
import com.webapp.arvand.arvandback.Repo.ReviewRepo;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {
    @Autowired
    private ReviewRepo reviewRepo;
    
    @Autowired
    private ProductRepo productRepo;

    public ReviewDto createReview(UserEntity user, CreateReviewRequest request) throws ApiException {
        ProductEntity product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "محصول یافت نشد"));

        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new ApiException(ApiErrorType.INVALID_REQUEST, "امتیاز باید بین 1 تا 5 باشد");
        }

        if (request.getComment() == null || request.getComment().trim().isEmpty()) {
            throw new ApiException(ApiErrorType.INVALID_REQUEST, "نظر الزامی است");
        }

        ReviewEntity existingReview = reviewRepo.findByProductAndUser(product, user).orElse(null);
        if (existingReview != null) {
            throw new ApiException(ApiErrorType.INVALID_REQUEST, "شما قبلاً نظری برای این محصول ثبت کرده‌اید");
        }

        ReviewEntity review = ReviewEntity.builder()
                .product(product)
                .user(user)
                .rating(request.getRating())
                .title(request.getTitle())
                .comment(request.getComment())
                .status(ReviewEntity.ReviewStatus.PENDING)
                .helpfulCount(0)
                .build();

        ReviewEntity savedReview = reviewRepo.save(review);
        return mapToDto(savedReview);
    }

    public Page<ReviewDto> getProductReviews(Long productId, Pageable pageable) throws ApiException {
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "محصول یافت نشد"));

        return reviewRepo.findByProductAndStatus(product, ReviewEntity.ReviewStatus.APPROVED, pageable)
                .map(this::mapToDto);
    }

    public ReviewDto updateReview(UserEntity user, Long reviewId, CreateReviewRequest request) throws ApiException {
        ReviewEntity review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "نظر یافت نشد"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new ApiException(ApiErrorType.UNAUTHORIZED, "دسترسی غیر مجاز");
        }

        if (request.getRating() != null) {
            if (request.getRating() < 1 || request.getRating() > 5) {
                throw new ApiException(ApiErrorType.INVALID_REQUEST, "امتیاز باید بین 1 تا 5 باشد");
            }
            review.setRating(request.getRating());
        }

        if (request.getTitle() != null) {
            review.setTitle(request.getTitle());
        }

        if (request.getComment() != null && !request.getComment().trim().isEmpty()) {
            review.setComment(request.getComment());
        }

        ReviewEntity updatedReview = reviewRepo.save(review);
        return mapToDto(updatedReview);
    }

    public void deleteReview(UserEntity user, Long reviewId) throws ApiException {
        ReviewEntity review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "نظر یافت نشد"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new ApiException(ApiErrorType.UNAUTHORIZED, "دسترسی غیر مجاز");
        }

        reviewRepo.delete(review);
    }

    public Double getProductAverageRating(Long productId) throws ApiException {
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "محصول یافت نشد"));

        Double averageRating = reviewRepo.getAverageRatingByProduct(product);
        return averageRating != null ? averageRating : 0.0;
    }

    public Long getProductReviewCount(Long productId) throws ApiException {
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new ApiException(ApiErrorType.NOT_FOUND, "محصول یافت نشد"));

        return reviewRepo.getReviewCountByProduct(product);
    }

    public List<ReviewDto> getUserReviews(UserEntity user) {
        return reviewRepo.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReviewDto mapToDto(ReviewEntity review) {
        return ReviewDto.builder()
                .id(review.getId())
                .productId(review.getProduct().getId())
                .userId(review.getUser().getId())
                .userName(review.getUser().getFullname())
                .rating(review.getRating())
                .title(review.getTitle())
                .comment(review.getComment())
                .helpfulCount(review.getHelpfulCount())
                .status(review.getStatus().name())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
