package com.ibm.bh6.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ibm.bh6.dao.impl.DBLocationQueryImpl;
import com.ibm.bh6.model.Location;

@Path("/location")
/**
 * CRUD service of location entities. It uses REST style.
 *
 */
public class LocationResource {

	@GET
	@Path("/{id}")
	/*
	 * URL Example: http://bbhackathon6.mybluemix.net/api/location/2 Returns
	 * JSON for one specific location by ID *
	 */
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") String id) {

		DBLocationQueryImpl q = new DBLocationQueryImpl();
		Location l = q.getLocation(Integer.parseInt(id));

		return Response.ok(getJSON(l).toString()).build();
	}

	@GET
	@Path("/byDistance")
	/*
	 * URL Example
	 * http://bbhackathon6.mybluemix.net/api/location/byDistance?x=9.3&y=3
	 * Return a JSON List with locations
	 */
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocationByDistance(@QueryParam("x") float x, @QueryParam("x") float y) {
		/*
		 * TODO: Use this code, after persistence is implemented and we have some example data
		 * 
		 * DBLocationQueryImpl q = new DBLocationQueryImpl();
		 *List<Location> locations = q.getLocationsByDistance(x, y);
		 * return Response.ok(getJSON(locations).toString()).build();
		*/
		
		
		Location l1  = new Location ("Hotel1","HOTEL");
		l1.setAddress("Berlin", "Kurf�rstendamm", new Integer(100), new Integer (10709), new Float(33.00), new Float(45.00));		

		Location l2 = new Location ("Berlin","CITY");
		l2.setAddress("Berlin", "n/a", -100, 10000,  new Float(33.00), new Float(45.00));
		
		Location l3 = new Location ("DKB","CUSTOMER");
		l3.setAddress("Berlin", "Schönhauserstr." , new Integer (765) , new Integer (10709) , new Float( 43.00), new Float (34.00));
		
		ArrayList<Location> locations = new ArrayList<Location>();
		locations.add(l1);
		locations.add(l2);
		locations.add(l3);
		
		
		return Response.ok(getJSON(locations).toString()).build();
	}

	private JsonElement getJSON(Collection<Location> locations) {
		JsonArray resultArray = new JsonArray();

		for (Iterator<Location> it = locations.iterator(); it.hasNext();) {
			JsonElement e = getJSON((Location) it.next());
			resultArray.add(e);
		}

		return resultArray;
	}

	private JsonElement getJSON(Location l) {
		
		if (l==null) {
			return new JsonObject();
		}
		
		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("id", "1"); // TODO: Location hat keine ID
		jsonObject.addProperty("name", l.getName());
		jsonObject.addProperty("type", l.getLocType());

		JsonObject gps = new JsonObject();
		gps.addProperty("x", l.getGPSx());
		gps.addProperty("y", l.getGPSy());
		jsonObject.add("gps", gps);

		JsonObject adr = new JsonObject();
		adr.addProperty("street", l.getStreet());
		adr.addProperty("number", l.gethnr());
		adr.addProperty("plz", l.getBrick());
		adr.addProperty("stadt", l.getCity());
		jsonObject.add("adress", adr);

		return jsonObject;
	}

}
