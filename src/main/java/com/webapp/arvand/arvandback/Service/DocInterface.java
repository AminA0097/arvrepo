package com.webapp.arvand.arvandback.Service;

import com.webapp.arvand.arvandback.Dto.DocDto;
import com.webapp.arvand.arvandback.Utills.ApiException;

public interface DocInterface {
    public DocDto downloadById(String id)throws ApiException;
}
