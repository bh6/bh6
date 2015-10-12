package com.ibm.bh6.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "checkin")
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "check_in_id", length = 255)
    private int checkInId;

    @Column(name = "timestamp")
    private Date m_timestamp;

    @JoinColumn(name = "user")
    private User m_user;

    @JoinColumn(name = "location")
    private Location location;

    public CheckIn() {
        m_timestamp = new Date();
    }

    public int getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(int checkInId) {
        this.checkInId = checkInId;
    }

    public Date getTimestamp() {
        return m_timestamp;
    }

    public void setTimestamp(Date m_timestamp) {
        this.m_timestamp = m_timestamp;
    }

    public User getUser() {
        return m_user;
    }

    public void setUser(User m_user) {
        this.m_user = m_user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
