package com.webapp.arvand.arvandback.AAEventService;


import com.webapp.arvand.arvandback.Utills.DateUtil;

import java.util.Date;

public class EventSimple {
    private String id;
    private String docId;
    private String title;
    private boolean inGrid;
    private String address;
    private String startDate;
    private String endDate;

    public EventSimple(String id, String docId, String title, boolean inGrid, String address, Date startDate, Date endDate) {
        this.id = id;
        this.docId = docId;
        this.title = title;
        this.inGrid = inGrid;
        this.address = address;
        this.startDate = DateUtil.format(startDate,"fa","yyyy/mm/dd");
        this.endDate = DateUtil.format(endDate,"fa","yyyy/mm/dd");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isInGrid() {
        return inGrid;
    }

    public void setInGrid(boolean inGrid) {
        this.inGrid = inGrid;
    }

}
