package com.webapp.arvand.arvandback.AmazingService;

import com.webapp.arvand.arvandback.AAProductService.ProductSimple;

import java.util.Date;
import java.util.List;

public class AmazingSimple {
    private String id;
    private Date duration;
    private String name;
    private List<ProductSimple> productList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public List<ProductSimple> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductSimple> productList) {
        this.productList = productList;
    }
}
