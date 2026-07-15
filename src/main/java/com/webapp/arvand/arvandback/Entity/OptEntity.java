package com.webapp.arvand.arvandback.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="TBL_OPT")
public class OptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLD_ID")
    private String id;

    @Column(name = "FLD_OPT_VALUE")
    private String optValue;

    @Column(name = "FLD_PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "FLD_USED")
    private boolean used;

    @Column(name = "FLD_EXPIRE_AT")
    private Date expireAt;

    @Column(name = "FLD_TYPE")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOptValue() {
        return optValue;
    }

    public void setOptValue(String optValue) {
        this.optValue = optValue;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }
}
