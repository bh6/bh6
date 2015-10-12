package com.ibm.bh6.rest;

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
import com.google.gson.JsonObject;

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
		
		JsonArray resultArray = new JsonArray();
		JsonObject jsonObject;

		jsonObject = new JsonObject();
		jsonObject.addProperty("id", "1");
		jsonObject.addProperty("name", "Mercure");
		jsonObject.addProperty("type", "Hotel");
		
		
		JsonObject gps = new JsonObject();
		gps.addProperty("x", x);
		gps.addProperty("y", y);
		
		JsonObject adr = new JsonObject();
		adr.addProperty("street", "Hauptstr.");
		adr.addProperty("number", "3");
		adr.addProperty("plz", "12345");
		jsonObject.add("adress", adr);
		
		resultArray.add(jsonObject);
		
		return Response.ok(resultArray.toString()).build();
	}
	
	
	
	
	
}
