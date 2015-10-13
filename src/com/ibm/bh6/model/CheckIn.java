package com.ibm.bh6.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ibm.bh6.dao.DBLocationQuery;
import com.ibm.bh6.dao.DBUserQuery;
import com.ibm.bh6.dao.impl.DBLocationQueryImpl;
import com.ibm.bh6.dao.impl.DBUserQueryImpl;

@Entity
@Table(name = "checkin")
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "check_in_id", length = 255)
    private int checkInId;

    @Column(name = "timestamp")
    private Date m_timestamp;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
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

    public void setUserByUserID(int userid) {
        // TODO Das Userobject aus der db laden
        DBUserQuery db = new DBUserQueryImpl();

        this.m_user = db.getUser(userid);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocationByLocationID(int locationid) {
        DBLocationQuery db = new DBLocationQueryImpl();
        this.location = db.getLocation(locationid);
    }

    public String toString() {
        return "CheckinID: " + checkInId + ", User:" + m_user.getUserId() + ", Timestamp: " + m_timestamp.toString() + ", Location: " + location.getName();
    }

}
