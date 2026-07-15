package com.webapp.arvand.arvandback.AAProductService;

import com.webapp.arvand.arvandback.AAProductVarientService.ProductVariantEntity;
import com.webapp.arvand.arvandback.CategorySerivce.CategoryEntity;
import com.webapp.arvand.arvandback.Entity.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "TBL_PRODUCT")
public class ProductEntity implements Serializable {
    

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID")
    private String id;

    @Column(name = "FLD_NAME", nullable = false)
    private String name;

    @Column(name = "FLD_TITLE")
    private String title;

    @JoinColumn(name = "FLD_IMG_URL")
    @ManyToOne
    private DocEntity imgUrl;

    @Column(name = "FLD_VIEWS")
    private Long views = 0L;

    @Column(name = "FLD_DESC")
    private String desc;

    @Column(name = "FLD_RANK")
    private Long rank = 0L;

    @Column(name = "FLD_CREATED_AT")
    private Date createdAt;

    @Column(name = "FLD_DELETED")
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLD_CATEGORY_ID")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLD_SUB_CATEGORY_ID")
    private CategoryEntity subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLD_SKIN_ID")
    private CoreThingEntity skin;

    @ManyToMany
    @JoinTable(
            name = "TBL_PRODUCT_TYPE",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CORE_THING_ID")
    )
    private Set<TypeEntity> types;

    @ManyToMany
    @JoinTable(
            name = "TBL_PRODUCT_TAGS",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CORE_THING_ID")
    )
    private Set<CoreThingEntity> tags;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ProductGalleryEntity> gallery;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ProductVariantEntity> variants;


    public CategoryEntity getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(CategoryEntity subCategory) {
        this.subCategory = subCategory;
    }

    public Set<ProductVariantEntity> getVariants() {
        return variants;
    }

    public void setVariants(Set<ProductVariantEntity> variants) {
        this.variants = variants;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public CoreThingEntity getSkin() {
        return skin;
    }

    public void setSkin(CoreThingEntity skin) {
        this.skin = skin;
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

    public DocEntity getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(DocEntity imgUrl) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public Set<TypeEntity> getTypes() {
        return types;
    }

    public void setTypes(Set<TypeEntity> types) {
        this.types = types;
    }

    public Set<CoreThingEntity> getTags() {
        return tags;
    }

    public void setTags(Set<CoreThingEntity> tags) {
        this.tags = tags;
    }

    public Set<ProductGalleryEntity> getGallery() {
        return gallery;
    }

    public void setGallery(Set<ProductGalleryEntity> gallery) {
        this.gallery = gallery;
    }
}