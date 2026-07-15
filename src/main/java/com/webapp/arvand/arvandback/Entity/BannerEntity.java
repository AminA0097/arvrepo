package com.webapp.arvand.arvandback.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TBL_BANNER")
public class BannerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID")
    private String id;

    @Column(name = "FLD_TITLE")
    String title;

    @Column(name = "FLD_DESC")
    String description;

    @Column(name = "FLD_IMG_URL")
    String backgroundImage;

    @Column(name = "FLD_HREF")
    String href;

    @Column(name = "FLD_BTN")
    String btnText;

    @Column(name = "FLD_BADGE")
    String badge;

    @Column(name = "FLD_IS_ACTIVE")
    boolean active; ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
