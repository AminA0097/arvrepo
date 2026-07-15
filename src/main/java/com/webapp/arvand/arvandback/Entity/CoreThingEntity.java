package com.webapp.arvand.arvandback.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="TBL_CORE_THING")
public class CoreThingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "FLD_GROUP")
    private String group;

    @Column(name = "FLD_EN_NAME")
    private String enName;

    @Column(name = "FLD_FA_NAME")
    private String faName;

    @Column(name = "FLD_VALUE")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getFaName() {
        return faName;
    }

    public void setFaName(String faName) {
        this.faName = faName;
    }
}
