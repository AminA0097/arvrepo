package com.webapp.arvand.arvandback.AAProductVarientService;

import com.webapp.arvand.arvandback.AAProductService.ProductEntity;
import com.webapp.arvand.arvandback.AAProductVariantValueService.ProductVariantValueEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "TBL_PRODUCT_VARIANT")
public class ProductVariantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLD_PRODUCT_ID", nullable = false)
    private ProductEntity product;

    @Column(name = "FLD_SKU", unique = true, nullable = false)
    private String sku;

    @Column(name = "FLD_DISCOUNT")
    private int discount;

    @Column(name = "FLD_STOCK_QUANTITY", nullable = false)
    private int stockQuantity;

    @Column(name = "FLD_PRICE", nullable = false)
    private Long price;

    @OneToMany(
            mappedBy = "variant",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ProductVariantValueEntity> variantValues;

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Set<ProductVariantValueEntity> getVariantValues() {
        return variantValues;
    }

    public void setVariantValues(Set<ProductVariantValueEntity> variantValues) {
        this.variantValues = variantValues;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
