package com.webapp.arvand.arvandback.EventService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<EventEntity, String> {

    @Query("""
        select new com.webapp.arvand.arvandback.EventService.EventSimple(
            e.id,
            e.docEntity.id,
            e.title,
            e.inGrid,
            e.address,
            e.startDate,
            e.endDate,e.desc,e.startTime,e.endTime
        )
        from EventEntity e
        where e.deleted = false 
    """)
    Page<EventSimple> findEventForHome(Pageable pageable);
}

