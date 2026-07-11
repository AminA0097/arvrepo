package com.webapp.arvand.arvandback.AAmazingCenter;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="TBL_AMAZING_CENTER")
public class AmazingCenterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "FLD_NAME")
    private String name;

    @Column(name = "FLD_IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "FLD_DURATION")
    private Date duration;

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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }
}
