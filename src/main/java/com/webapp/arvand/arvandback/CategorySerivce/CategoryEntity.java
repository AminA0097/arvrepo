package com.webapp.arvand.arvandback.CategorySerivce;

import com.webapp.arvand.arvandback.Entity.DocEntity;
import jakarta.persistence.*;

@Entity
@Table(name="TBL_CATEGORY")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "FLD_FA_NAME", nullable = false, length = 100)
    private String faName;

    @Column(name = "FLD_EN_NAME", length = 100)
    private String enName;

    @Column(name = "FLD_SLUG", unique = true, nullable = false, length = 100)
    private String slug;

    @JoinColumn(name = "FLD_PIC_URL")
    @ManyToOne
    private DocEntity docEntity;

    @Column(name = "FLD_IN_GRID")
    private boolean inGrid = false;

    @Column(name = "FLD_DELETED")
    private boolean deleted = false;

    @Column(name = "FLD_LEVEL_TYPE")
    private String levelType;

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
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

    public DocEntity getDocEntity() {
        return docEntity;
    }

    public void setDocEntity(DocEntity docEntity) {
        this.docEntity = docEntity;
    }

    public boolean isInGrid() {
        return inGrid;
    }

    public void setInGrid(boolean inGrid) {
        this.inGrid = inGrid;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
