package com.webapp.arvand.arvandback.SearchService;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "TBL_PRODUCT_SEARCH")
@Entity
public class ProductSearchEntity {

    @Id
    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "FLD_TITLE", nullable = false)
    private String title;

    @Column(name = "FLD_SEARCH_TEXT ", nullable = false)
    private String normalizedTitle;

    @Column(name = "FLD_IMAGE_URL")
    private String imageUrl;

    @Column(name = "FLD_RANK")
    private int rank;

    @Column(name = "FLD_VIEWS")
    private int views;

    @Column(name = "FLD_DETAIL")
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNormalizedTitle() {
        return normalizedTitle;
    }

    public void setNormalizedTitle(String normalizedTitle) {
        this.normalizedTitle = normalizedTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
