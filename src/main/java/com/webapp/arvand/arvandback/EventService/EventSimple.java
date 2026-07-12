package com.webapp.arvand.arvandback.EventService;


import com.webapp.arvand.arvandback.Utills.DateUtil;

import java.time.LocalTime;
import java.util.Date;

public class EventSimple {
    private String id;
    private String docId;
    private String title;
    private boolean inGrid;
    private String address;
    private String startDate;
    private String endDate;
    private String desc;
    private LocalTime startTime;
    private LocalTime endTime;

    public EventSimple(String id, String docId, String title, boolean inGrid, String address, Date startDate, Date endDate,String desc,
                       LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.docId = docId;
        this.title = title;
        this.inGrid = inGrid;
        this.address = address;
        this.startDate = DateUtil.format(startDate,"fa","yyyy/mm/dd");
        this.endDate = DateUtil.format(endDate,"fa","yyyy/mm/dd");
        this.desc = desc;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
