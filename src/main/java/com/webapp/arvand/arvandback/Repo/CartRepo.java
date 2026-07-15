package com.webapp.arvand.arvandback.Repo;

import com.webapp.arvand.arvandback.Entity.CartEntity;
import com.webapp.arvand.arvandback.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUser(UserEntity user);
}
