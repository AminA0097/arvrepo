package com.webapp.arvand.arvandback.AAProductService;

public class ProductSimple {

    private String id;

    private String name;

    private String title;

    private Long price;

    private int discount;

    private int stock = 0;

    private String imgUrl;

    private Long views = 0L;

    private Long rank = 0L;

    public ProductSimple(String id, String name, String title, Long price, int discount, Integer stock, String imgUrl, Long views, Long rank) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.imgUrl = imgUrl;
        this.views = views;
        this.rank = rank;
    }

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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
}
