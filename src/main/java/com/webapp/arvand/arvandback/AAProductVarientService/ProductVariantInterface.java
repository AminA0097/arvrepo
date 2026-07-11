package com.webapp.arvand.arvandback.AAProductVarientService;

import com.webapp.arvand.arvandback.Utills.ApiException;

import java.util.List;

public interface ProductVariantInterface {

    public ProductVariantsResponseDto getVariantsById(String id)throws ApiException;
}
