package com.webapp.arvand.arvandback.Entity;

import com.webapp.arvand.arvandback.AAProductService.ProductEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_PRODUCT_GALLERY")
public class ProductGalleryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLD_PRODUCT_ID")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLD_DOC_ID")
    private DocEntity image;

    @Column(name = "FLD_SORT_ORDER")
    private Integer sortOrder = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public DocEntity getImage() {
        return image;
    }

    public void setImage(DocEntity image) {
        this.image = image;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
