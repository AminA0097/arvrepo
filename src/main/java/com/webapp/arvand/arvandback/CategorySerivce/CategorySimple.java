package com.webapp.arvand.arvandback.CategorySerivce;


public class CategorySimple {
    private String id;
    private String faName;
    private String slug;
    private String picUrl;
    private boolean inGrid;
    private Long countt;

    public CategorySimple(String id, String faName, String slug, String picUrl, boolean inGrid, Long count){

        this.id = id;
        this.faName = faName;
        this.slug = slug;
        this.picUrl = picUrl;
        this.inGrid = inGrid;
        this.countt = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaName() {
        return faName;
    }

    public void setFaName(String faName) {
        this.faName = faName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public boolean isInGrid() {
        return inGrid;
    }

    public void setInGrid(boolean inGrid) {
        this.inGrid = inGrid;
    }

    public Long getCountt() {
        return countt;
    }

    public void setCountt(Long countt) {
        this.countt = countt;
    }

    public String getHref() {
        return "/category/" + slug;
    }
}
