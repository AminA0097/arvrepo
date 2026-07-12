package com.webapp.arvand.arvandback.AmazingService;

import com.webapp.arvand.arvandback.AAmazingCenter.AmazingCenterEntity;
import com.webapp.arvand.arvandback.AAProductService.ProductEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="TBL_AMAZING")
public class AmazingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "FLD_IS_ACTIVE")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "FLD_AMAZING_CNETER")
    private AmazingCenterEntity amazingCenter;

    @ManyToMany
    @JoinTable(
            name = "TBL_AMAZING_PRO",
            joinColumns = @JoinColumn(name = "AMAZING_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRO_ID")
    )
    private Set<ProductEntity> productEntities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public AmazingCenterEntity getAmazingCenter() {
        return amazingCenter;
    }

    public void setAmazingCenter(AmazingCenterEntity amazingCenter) {
        this.amazingCenter = amazingCenter;
    }

    public Set<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(Set<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }
}
