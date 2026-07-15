package com.webapp.arvand.arvandback.AApi.Controller;

import com.webapp.arvand.arvandback.AAProductService.ProductDetailSimple;
import com.webapp.arvand.arvandback.AAProductVarientService.ProductVariantInterface;
import com.webapp.arvand.arvandback.AAProductVarientService.ProductVariantsResponseDto;
import com.webapp.arvand.arvandback.AmazingService.AmazingInterface;
import com.webapp.arvand.arvandback.AmazingService.AmazingSimple;
import com.webapp.arvand.arvandback.CategorySerivce.CategoryInterface;
import com.webapp.arvand.arvandback.Dto.DocDto;
import com.webapp.arvand.arvandback.EventService.EventInterface;
import com.webapp.arvand.arvandback.EventService.EventSimple;
import com.webapp.arvand.arvandback.AAProductService.ProductInterface;
import com.webapp.arvand.arvandback.AAProductService.ProductSearchReq;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.ApiResponse;
import com.webapp.arvand.arvandback.Utills.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiRestController {
    @Autowired
    private CategoryInterface categoryInterface;
    @Autowired
    private EventInterface eventInterface;
    @Autowired
    private AmazingInterface amazingInterface;
    @Autowired
    private ProductInterface productInterface;
    @Autowired
    private ProductVariantInterface variantInterface;

    @GetMapping("/event/get-all")
    public ResponseEntity<ApiResponse> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) throws ApiException {
        PageResponse<EventSimple> eventSimplePageResponse =
                eventInterface.getEventsForHome(page, size, sortBy, direction);
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(eventSimplePageResponse));
    }

    @GetMapping("/amazing/get-all")
    public ResponseEntity<ApiResponse> getAllAmazing(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) throws ApiException {
        AmazingSimple amazingSimple =
                amazingInterface.getAll(page, size);
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(amazingSimple));
    }


    @GetMapping("/category/image/{categoryId}")
    public ResponseEntity<byte[]> getCategoryImage(@PathVariable String categoryId) throws ApiException {
        DocDto docDto = categoryInterface.getImage(categoryId);
        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(docDto.getDocType()))
                .header(HttpHeaders.CACHE_CONTROL, "max-age=86400, public")
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + docDto.getDocName() + "\"")
                .body(docDto.getDocData());
    }

    @PostMapping("/product/by-filter")
    public ResponseEntity<ApiResponse> getProductByFilter(
            @RequestBody ProductSearchReq productSearchReq
    ) throws ApiException {
        return ResponseEntity.ok().body(ApiResponse.success(productInterface.search(productSearchReq)));
    }

    @GetMapping("/product/by-id/{id}")
    public ResponseEntity<ApiResponse> getProductById(
            @PathVariable String id
    ) throws ApiException {
        ProductDetailSimple productSimple = productInterface.getById(id);
        return ResponseEntity.ok().body(ApiResponse.success(productSimple));

    }
    @GetMapping("/product/gallery/{id}")
    public ResponseEntity<ApiResponse> getProductGallery(
            @PathVariable String id
    ) throws ApiException {
        List<String> gallery = productInterface.getProductGallery(id);
        return ResponseEntity.ok().body(ApiResponse.success(gallery));

    }
    @GetMapping("/product/futures/{id}")
    public ResponseEntity<ApiResponse> getProductFuture(
            @PathVariable String id
    ) throws ApiException {
        List<Map<String,String>> gallery = productInterface.getFutures(id);
        return ResponseEntity.ok().body(ApiResponse.success(gallery));

    }
    @GetMapping("/variants/by-id/{id}")
    public ResponseEntity<ApiResponse> getVariants(
            @PathVariable String id
    ) throws ApiException {
        ProductVariantsResponseDto variants = variantInterface.getVariantsById(id);
        return ResponseEntity.ok().body(ApiResponse.success(variants));

    }
}
