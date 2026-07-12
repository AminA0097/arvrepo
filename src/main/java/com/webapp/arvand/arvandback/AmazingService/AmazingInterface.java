package com.webapp.arvand.arvandback.AmazingService;

import com.webapp.arvand.arvandback.Utills.ApiException;

public interface AmazingInterface {
    public AmazingSimple getAll(int pageNo , int pageSize)throws ApiException;
}
