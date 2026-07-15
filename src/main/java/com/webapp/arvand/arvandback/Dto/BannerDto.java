package com.webapp.arvand.arvandback.Dto;

import com.webapp.arvand.arvandback.Entity.BannerEntity;

public class BannerDto {
    private String id;
    private String title;
    private String description;
    private String backgroundImage;
    private String href;
    private String btnText;
    private String badge;

    public BannerDto() {
    }

    public BannerDto(BannerEntity bannerEntity) {
        this.id = bannerEntity.getId() == null ? "-1" : bannerEntity.getId();
        this.title = bannerEntity.getTitle() == null ? "unTitled" : bannerEntity.getTitle();
        this.description = bannerEntity.getDescription() == null ? "unDescription" : bannerEntity.getDescription();
        this.backgroundImage = bannerEntity.getBackgroundImage() == null ? "unBackgroundImage" : bannerEntity.getBackgroundImage();
        this.href = bannerEntity.getHref() == null ? "unHref" : bannerEntity.getHref();
        this.btnText = bannerEntity.getBtnText() == null ? "unBtnText" : bannerEntity.getBtnText();
        this.badge = bannerEntity.getBadge() == null ? "unBadge" : bannerEntity.getBadge();
    }

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
}
