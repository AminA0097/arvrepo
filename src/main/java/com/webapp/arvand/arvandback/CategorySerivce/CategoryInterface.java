package com.webapp.arvand.arvandback.CategorySerivce;

import com.webapp.arvand.arvandback.Dto.DocDto;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.PageResponse;

public interface CategoryInterface {
    public PageResponse<CategorySimple> getCategoriesWithItsCount(int page, int size, String sortBy, String direction) throws ApiException;

    public boolean saveCategory(CategoryForm dto)throws ApiException;

    public CategoryForm getCategory(String id)throws ApiException;

    public DocDto getImage(String categoryId)throws ApiException;

    public String getCategoriesCount()throws ApiException;
}
