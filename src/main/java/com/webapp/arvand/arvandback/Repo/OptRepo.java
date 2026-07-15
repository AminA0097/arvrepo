package com.webapp.arvand.arvandback.Repo;

import com.webapp.arvand.arvandback.Entity.OptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OptRepo extends JpaRepository<OptEntity,String> {
    @Query("SELECT o FROM OptEntity o WHERE o.phoneNumber = :phoneNumber " +
            "AND o.optValue = :optValue " +
            "AND o.used = false " +
            "AND o.expireAt > :currentTime AND o.type = :type")
    Optional<OptEntity> findValidOtp(@Param("phoneNumber") String phoneNumber,
                                     @Param("optValue") String optValue,
                                     @Param("type") String type,
                                     @Param("currentTime") Date currentTime);
    List<OptEntity> findByPhoneNumber(String phoneNumber);
    @Transactional
    @Modifying
    @Query(
            "update OptEntity o "+
            "set o.used = true  "+
            "where o.phoneNumber = :phoneNumber " +
            "and o.used = false")
    void invalidateAllByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
