package com.webapp.arvand.arvandback.Repo;

import com.webapp.arvand.arvandback.Entity.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepo extends JpaRepository<BannerEntity,String> {
    List<BannerEntity> findByActiveTrue();
}
