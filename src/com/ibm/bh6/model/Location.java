package com.ibm.bh6.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id", length = 255)
    private int locationId;

    // Location
    @Column(name = "name")
    private String m_Name;

    @Column(name = "loc_type")
    private String m_LocType;

    // Address
    @Column(name = "city")
    private String m_City;
    @Column(name = "street")
    private String m_Street;
    @Column(name = "hnr")
    private Integer m_hnr;
    @Column(name = "brick")
    private Integer m_Brick;
    @Column(name = "gps_x")
    private Float m_GPSx;
    @Column(name = "gps_y")
    private Float m_GPSy;

    public Location() {
    };

    public Location(String inName, String inLocType) {
        m_Name = new String(inName);
        m_LocType = new String(inLocType);
    }

    public String getName() {
        return m_Name;
    }

    public String getLocType() {
        return m_LocType;
    }

    // Address

    public void setAddress(String inCity, String inStreet, Integer inhnr, Integer inBrick, Float inGPSx, Float inGPSy) {
        // TODO Auto-generated constructor stub
        m_City = new String(inCity);
        m_Street = new String(inStreet);
        m_hnr = new Integer(inhnr);
        m_Brick = new Integer(inBrick);
        m_GPSx = new Float(inGPSx);
        m_GPSy = new Float(inGPSy);
    }

    public String getStreet() {
        return m_Street;
    }

    public Integer gethnr() {
        return m_hnr;
    }

    public Integer getBrick() {
        return m_Brick;
    }

    public String getCity() {
        return m_City;
    }

    public Float getGPSx() {
        return m_GPSx;
    }

    public Float getGPSy() {
        return m_GPSy;
    }
}
