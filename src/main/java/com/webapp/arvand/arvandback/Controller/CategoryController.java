package com.webapp.arvand.arvandback.Controller;

import com.webapp.arvand.arvandback.Dto.DocDto;
import com.webapp.arvand.arvandback.CategorySerivce.CategoryForm;
import com.webapp.arvand.arvandback.CategorySerivce.CategorySimple;
import com.webapp.arvand.arvandback.CategorySerivce.CategoryInterface;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.ApiResponse;
import com.webapp.arvand.arvandback.Utills.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryInterface categoryInterface;


    @GetMapping("/admin/all")
    public ApiResponse<PageResponse<CategorySimple>> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "faName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) throws ApiException {
        PageResponse<CategorySimple> categoryDtos = categoryInterface.getCategoriesWithItsCount(
                page,
                size,
                sortBy,
                direction);
        return ApiResponse.success(categoryDtos);
    }

    @GetMapping("/admin/category")
    public ApiResponse<CategoryForm> getCategory(
            @RequestParam String id
    )
            throws ApiException {
        categoryInterface.getCategory(id);
        return null;
    }

    @PostMapping("/admin/save")
    public ResponseEntity<ApiResponse> saveCategory(
            @ModelAttribute CategoryForm dto
    ) throws ApiException {
        categoryInterface.saveCategory(dto);
        return ResponseEntity.ok(
                ApiResponse.success("ثبت با موفقیت انجام شد")
        );
    }
}

