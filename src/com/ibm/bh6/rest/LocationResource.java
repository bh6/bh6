package com.ibm.bh6.rest;

import java.util.Collection;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ibm.bh6.model.Location;

@Path("/location")
/**
 * CRUD service of location entities. It uses REST style.
 *
 */
public class LocationResource {

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") String id) {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", id);
		jsonObject.addProperty("name", "Mövenpick");
		jsonObject.addProperty("type", "Hotel");
		
	
		return Response.ok(jsonObject.toString()).build();
		
	}
	
	@GET
	@Path("/byDistance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocationByDistance(@QueryParam("x") double x,@QueryParam("x") double y) {
		
	
		return null;
		//return Response.ok(resultArray.toString()).build();
	}
	
	private JsonElement getJSON(Collection<Location> objects) {
		JsonArray resultArray = new JsonArray();
		
		for (Iterator<Location> it = objects.iterator(); it.hasNext();) {
			JsonElement e = getJSON((Location) it.next());
			resultArray.add(e);
		}
		
		return resultArray;
	}
	
	private JsonElement getJSON(Location l) {
		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("id", "1");   //TODO: Location hat keine ID
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
