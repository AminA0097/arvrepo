package com.webapp.arvand.arvandback.EventService;

import com.webapp.arvand.arvandback.Entity.DocEntity;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name="TBL_EVENT")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID", updatable = false, nullable = false)
    private String id;

    @JoinColumn(name = "FLD_PIC_URL")
    @ManyToOne
    private DocEntity docEntity;

    @Column(name = "FLD_IN_GRID")
    private boolean inGrid = false;

    @Column(name = "FLD_DELETED")
    private boolean deleted = false;

    @Column(name = "FLD_TITLE")
    private String title;

    @Column(name = "FLD_DESC")
    private String desc;

    @Column(name = "FLD_ADDRESS")
    private String address;

    @Column(name = "FLD_START_DATE")
    private Date startDate;

    @Column(name = "FLD_END_DATE")
    private Date endDate;

    @Column(name = "FLD_START_TIME")
    private LocalTime startTime;

    @Column(name = "FLD_END_TIME")
    private LocalTime endTime;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
