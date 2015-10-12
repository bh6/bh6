package com.ibm.bh6.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.bh6.dao.DBCheckinQuery;
import com.ibm.bh6.dao.impl.DBCheckinQueryImpl;
import com.ibm.bh6.model.CheckIn;

@Path("/checkin")
public class CheckInResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(CheckIn c) {
		DBCheckinQuery db = new DBCheckinQueryImpl();
		boolean result = db.createCheckIn(c);
		
		return Response.ok(result+": "+c.toString()).build();
	}
	
	
	
}

