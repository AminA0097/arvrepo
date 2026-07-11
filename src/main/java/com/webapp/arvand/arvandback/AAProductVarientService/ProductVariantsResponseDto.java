package com.webapp.arvand.arvandback.AAProductVarientService;

import java.util.ArrayList;
import java.util.List;

public class ProductVariantsResponseDto {
    private String productId;
    private List<VariantDto> variants = new ArrayList<>();

    public ProductVariantsResponseDto() {
    }


    public ProductVariantsResponseDto(String productId, List<VariantDto> variants) {
        this.productId = productId;
        this.variants = variants;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<VariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDto> variants) {
        this.variants = variants;
    }
}
