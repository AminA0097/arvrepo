package com.webapp.arvand.arvandback.AAProductService;

import com.webapp.arvand.arvandback.Dto.HotsSimple;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.PageResponse;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductInterface {
    public String getProductsCount()throws ApiException;
    public PageResponse search(ProductSearchReq req) throws ApiException;
    public ProductDetailSimple getById(String id) throws ApiException;
    public List<ProductDto> getAllNew(Pageable pageable)throws Exception;

    public PageResponse<HotsSimple> getProductsByType(int page, int size, String type);

    public PageResponse<ProductSimple> getProductsByViews(int page, int size, int views);

    public PageResponse<ProductSimple> getProductsByDis(int page, int size, int dis);

    List<String> getProductGallery(String id)throws ApiException;

    List<Map<String, String>> getFutures(String id)throws ApiException;
}
