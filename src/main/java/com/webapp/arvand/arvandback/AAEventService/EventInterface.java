package com.webapp.arvand.arvandback.AAEventService;

import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.PageResponse;

public interface EventInterface {
    public PageResponse<EventSimple> getEventsForHome(int page,int pageSize,String sortBy,String direction)throws ApiException;
}
