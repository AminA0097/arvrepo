package com.webapp.arvand.arvandback.AAProductService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProductDetailSimple {
    private String id;
    private String title;
    private String categoryId;
    private String categoryTitle;
    private Long price;
    private int discount;
    private Long views;
    private Long rank;
    private String imgUrl;
    private int stock;
    private String desc;

    public ProductDetailSimple(String id, String title, String categoryId, String categoryTitle, Long price,
                               int discount, Long views, Long rank, String imgUrl, int stock, String desc) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.price = price;
        this.discount = discount;
        this.views = views;
        this.rank = rank;
        this.imgUrl = imgUrl;
        this.stock = stock;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
