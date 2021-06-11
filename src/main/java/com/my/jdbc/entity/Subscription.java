package com.my.jdbc.entity;

import java.io.Serializable;
import java.util.Date;

public class Subscription implements Serializable {
    private static final long serialVersionUID = 7508151732152545879L;

    private long subId;
    private long userId;
    private long magazineId;
    private String magazineName;
    private Date startDate;
    private Date endDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getSubId() {
        return subId;
    }

    public void setSubId(long subId) {
        this.subId = subId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(long magazineId) {
        this.magazineId = magazineId;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
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

    @Override
    public String toString() {
        return "Subscription{" +
                "subId=" + subId +
                ", userId=" + userId +
                ", magazineId=" + magazineId +
                ", magazineName='" + magazineName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
