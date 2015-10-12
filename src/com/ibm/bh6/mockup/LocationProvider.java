package com.ibm.bh6.mockup;
import com.ibm.bh6.model.*;

public class LocationProvider {

	private static Location l1;
	private static Location l2;
	private static Location l3;
	
	public LocationProvider() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		l1  = new Location ("Hotel1","HOTEL");
		l1.setAddress("Berlin", "Kurfürstendamm", new Integer(100), new Integer (10709), new Float(33.00), new Float(45.00));		

		l2 = new Location ("Berlin","CITY");
		l2.setAddress("Berlin", "n/a", -100, 10000,  new Float(33.00), new Float(45.00));
		
		l3 = new Location ("DKB","CUSTOMER");
		l3.setAddress("Berlin", "Schönhauserstr." , new Integer (765) , new Integer (10709) , new Float( 43.00), new Float (34.00));
		
	}

	static Location getLocation(Integer inID) {
		if (inID==1)
			return l1;
		else if (inID==2)
			return l2;
		else if (inID==3)
			return l3;
		else return null;
	}
}

