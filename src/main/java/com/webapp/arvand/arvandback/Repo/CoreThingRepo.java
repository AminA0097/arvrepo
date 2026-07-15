package com.webapp.arvand.arvandback.Repo;

import com.webapp.arvand.arvandback.Entity.CoreThingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreThingRepo extends JpaRepository<CoreThingEntity, String> {
    CoreThingEntity findByValueAndGroup(String enName, String group);

}
