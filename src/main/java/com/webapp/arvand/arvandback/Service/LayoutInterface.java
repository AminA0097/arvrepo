package com.webapp.arvand.arvandback.Service;

import com.webapp.arvand.arvandback.Dto.BannerDto;
import com.webapp.arvand.arvandback.CategorySerivce.CategoryRequest;
import com.webapp.arvand.arvandback.Dto.TheBestDto;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LayoutInterface {



    public ResponseEntity<ApiResponse<List<BannerDto>>> getBanner()throws Exception;

    public ResponseEntity<ApiResponse> getCategories()throws ApiException;

    public ResponseEntity<ApiResponse<List<TheBestDto>>> getTheBest(String type)throws ApiException;

//    Categories Methods

    public <T> List<T> searchInCategory(String nativeQuery,Class<T> outPut,Map<String, Object> params)throws ApiException;

    ResponseEntity<ApiResponse> getCategoryById(CategoryRequest categoryRequest) throws ApiException;
}
