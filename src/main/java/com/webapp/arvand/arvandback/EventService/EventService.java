package com.webapp.arvand.arvandback.EventService;

import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventService implements EventInterface{
    @Autowired
    private EventRepo eventRepo;
    @Override
    public PageResponse<EventSimple> getEventsForHome(
            int page, int pageSize, String sortBy, String direction
    ) throws ApiException {
        try {
            Sort sort = Sort.by("id").descending();

            Pageable pageable = PageRequest.of(
                    0,
                    30,
                    Sort.by("id").descending()
            );
            Page<EventSimple> entities = eventRepo.findEventForHome(pageable);

            PageResponse<EventSimple> response = new PageResponse<>(
                    entities.getContent(),
                    entities.getNumber(),
                    entities.getTotalPages(),
                    entities.getTotalElements(),
                    entities.getSize()
            );
            return response;

        } catch (Exception e) {
            throw new ApiException(ApiErrorType.SERVER_ERROR,"Failed to retrieve all the events");
        }
    }
}
