package com.webapp.arvand.arvandback.Repo;

import com.webapp.arvand.arvandback.Entity.ReviewEntity;
import com.webapp.arvand.arvandback.AAProductService.ProductEntity;
import com.webapp.arvand.arvandback.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepo extends JpaRepository<ReviewEntity, Long> {
    Page<ReviewEntity> findByProductAndStatus(ProductEntity product, ReviewEntity.ReviewStatus status, Pageable pageable);
    
    Optional<ReviewEntity> findByProductAndUser(ProductEntity product, UserEntity user);
    
    @Query("SELECT AVG(r.rating) FROM ReviewEntity r WHERE r.product = :product AND r.status = 'APPROVED'")
    Double getAverageRatingByProduct(@Param("product") ProductEntity product);
    
    @Query("SELECT COUNT(r) FROM ReviewEntity r WHERE r.product = :product AND r.status = 'APPROVED'")
    Long getReviewCountByProduct(@Param("product") ProductEntity product);
    
    List<ReviewEntity> findByUserOrderByCreatedAtDesc(UserEntity user);
}
