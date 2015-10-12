package com.ibm.bh6.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;

@Path("/location")
/**
 * CRUD service of location entities. It uses REST style.
 *
 */
public class LocationResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam("id") long id) {
		JsonObject resultObject = new JsonObject();
		
		
		
		return Response.ok(resultObject.toString()).build();
		
	}
	
	
	
	
	
}
