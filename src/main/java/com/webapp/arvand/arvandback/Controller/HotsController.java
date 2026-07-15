package com.webapp.arvand.arvandback.Controller;

import com.webapp.arvand.arvandback.Dto.HotsSimple;
import com.webapp.arvand.arvandback.AAProductService.ProductInterface;
import com.webapp.arvand.arvandback.Utills.ApiResponse;
import com.webapp.arvand.arvandback.Utills.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hots")
public class HotsController {

    @Autowired
    private ProductInterface productInterface;
    @GetMapping("/products/by-type")
    public ApiResponse<PageResponse<HotsSimple>> getProductsByType(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50")int size,
            @RequestParam(defaultValue = "50") String type
    ){
        productInterface.getProductsByType(page,size,type);
        return null;
    }
}
