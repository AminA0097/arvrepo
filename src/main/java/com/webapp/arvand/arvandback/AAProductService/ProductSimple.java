package com.webapp.arvand.arvandback.AAProductService;

public class ProductSimple {

    private String id;
    private String name;
    private String title;
    private String imgUrl;
    private Long views;
    private Long rank;
    private Long price;
    private Long stock;
    private Long discount;
    private boolean available;


    public ProductSimple(String id, String name, String title, String imgUrl,
                         Long views, Long rank,
                         Long discount,Long price,Long stock,boolean available
    ) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.imgUrl = imgUrl;
        this.views = views;
        this.rank = rank;
        this.price = price;
        this.stock = stock;
        this.discount = discount;
        this.available = available;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
