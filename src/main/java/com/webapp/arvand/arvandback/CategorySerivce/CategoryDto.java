package com.webapp.arvand.arvandback.CategorySerivce;

public class CategoryDto {
    private Long id;
    private String name;
    private String imageUrl;
    private int productCount;
    private String description;

    public CategoryDto() {}

    public CategoryDto(String name, String imageUrl, int productCount) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.productCount = productCount;
    }

    public CategoryDto(Long id, String name, String imageUrl, int productCount, String description) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.productCount = productCount;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getProductCount() { return productCount; }
    public void setProductCount(int productCount) { this.productCount = productCount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}