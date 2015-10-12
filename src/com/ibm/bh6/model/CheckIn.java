package com.ibm.bh6.model;
import java.util.Date;

public class CheckIn {

	private Date m_timestamp;
		
	public CheckIn() {
		m_timestamp = new Date(); 
	}

	public String getCheckIn() {
		return m_timestamp.toString();
	}
}
