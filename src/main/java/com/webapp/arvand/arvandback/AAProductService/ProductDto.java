package com.webapp.arvand.arvandback.AAProductService;

import java.util.Map;


public class ProductDto {
    public ProductDto(
            ProductEntity productEntity
            ) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.price = price;
        this.discount = discount;
        this.isNew = isNew;
        this.isBestSeller = isBestSeller;
        this.imgUrl = imgUrl;
        this.views = views;
        this.rand = rand;
        this.createAt = createAt;
        this.categoryTitle = categoryTitle;
        this.categoryId = categoryId;
        this.audienceTitle = audienceTitle;
        this.audienceId = audienceId;
        this.tags = tags;
    }

    public ProductDto() {
    }

    private String id;

    private String name;
    private String title;

    private long price;
    private int discount;

    private boolean isNew;
    private boolean isBestSeller;

    private String imgUrl;

    private int views;
    private int rand;

    private String createAt;

    private String categoryTitle;
    private String categoryId;

    private String audienceTitle;
    private String audienceId;

    private Map<String,String> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isBestSeller() {
        return isBestSeller;
    }

    public void setBestSeller(boolean bestSeller) {
        isBestSeller = bestSeller;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getRand() {
        return rand;
    }

    public void setRand(int rand) {
        this.rand = rand;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAudienceTitle() {
        return audienceTitle;
    }

    public void setAudienceTitle(String audienceTitle) {
        this.audienceTitle = audienceTitle;
    }

    public String getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(String audienceId) {
        this.audienceId = audienceId;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }
}
