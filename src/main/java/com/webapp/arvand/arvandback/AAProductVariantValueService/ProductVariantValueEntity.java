package com.webapp.arvand.arvandback.AAProductVariantValueService;

import com.webapp.arvand.arvandback.AAProductVarientService.ProductVariantEntity;
import com.webapp.arvand.arvandback.Entity.CoreThingEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_PRODUCT_VARIANT_VALUE")
public class ProductVariantValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLD_VARIANT_ID", nullable = false)
    private ProductVariantEntity variant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLD_CORE_THING_ID", nullable = false)
    private CoreThingEntity coreThing;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductVariantEntity getVariant() {
        return variant;
    }

    public void setVariant(ProductVariantEntity variant) {
        this.variant = variant;
    }

    public CoreThingEntity getCoreThing() {
        return coreThing;
    }

    public void setCoreThing(CoreThingEntity coreThing) {
        this.coreThing = coreThing;
    }
}
