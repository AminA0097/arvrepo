package com.webapp.arvand.arvandback.CategorySerivce;

import org.springframework.web.multipart.MultipartFile;

public class CategoryForm {

    private String id;
    private String faName;
    private String enName;
    private String slug;
    private Boolean inGrid;
    private MultipartFile imageFile;
    private String imageUrl;

    public CategoryForm() {
    }
    public CategoryForm(CategoryEntity category) {

        this.id = category.getId() == null ? null : category.getId();
        this.faName = category.getFaName() == null ? null : category.getFaName();
        this.enName = category.getEnName() == null ? null : category.getEnName();
        this.slug = category.getSlug() == null ? null : category.getSlug();
        this.inGrid = category.isInGrid();

        this.imageUrl = "/api/layout/category/image/" + category.getId();
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

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getInGrid() {
        return inGrid;
    }

    public void setInGrid(Boolean inGrid) {
        this.inGrid = inGrid;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
