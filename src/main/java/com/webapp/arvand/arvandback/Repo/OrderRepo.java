package com.webapp.arvand.arvandback.Repo;

import com.webapp.arvand.arvandback.Entity.OrderEntity;
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
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findByUser(UserEntity user, Pageable pageable);
    
    List<OrderEntity> findByUserOrderByCreatedAtDesc(UserEntity user);
    
    @Query("SELECT o FROM OrderEntity o WHERE o.user = :user AND o.status = :status")
    List<OrderEntity> findByUserAndStatus(@Param("user") UserEntity user, @Param("status") OrderEntity.OrderStatus status);
    
    Optional<OrderEntity> findById(Long id);
}
