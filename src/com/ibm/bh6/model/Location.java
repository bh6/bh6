package com.ibm.bh6.model;

public class Location {
	// Location
	private String m_Name;

	private String m_LocType;

	// Address
	private String m_City;
	private String m_Street;
	private Integer m_hnr;
	private Integer m_Brick;
	private Float m_GPSx;
	private Float m_GPSy;

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
