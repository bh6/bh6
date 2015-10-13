package com.ibm.bh6.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.bh6.dao.DBCheckinQuery;
import com.ibm.bh6.dao.DBLocationQuery;
import com.ibm.bh6.dao.DBUserQuery;
import com.ibm.bh6.dao.impl.DBCheckinQueryImpl;
import com.ibm.bh6.dao.impl.DBLocationQueryImpl;
import com.ibm.bh6.dao.impl.DBUserQueryImpl;
import com.ibm.bh6.model.CheckIn;
import com.ibm.bh6.model.Location;
import com.ibm.bh6.model.User;

@Path("/checkin")
public class CheckInResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(@Context HttpServletRequest request, CheckIn c) {

		System.out.println(request.getSession());

		// Get user from session
		Object o = request.getSession().getAttribute("currentUser");
		if (o != null && o instanceof User)
			c.setUser((User) o);

		// Now do some checks
		if (c.getUser() == null) {
			return CORSResponse.ok("User null").build();
		} else {
			System.out.println("User for checkin: " + c.getUser().getUserName());
		}
		if (c.getLocation() == null) {
			return CORSResponse.ok("Location null").build();
		} else {
			System.out.println("Location for checkin: " + c.getLocation().getName());
		}

		DBCheckinQuery db = new DBCheckinQueryImpl();
		boolean result = db.createCheckIn(c);

		return CORSResponse.ok(result + ": " + c.toString()).build();
	}

	/*
	 * 
	 * @GET
	 * 
	 * @Path("createFakeCheckins") public Response createFakeCheckins() {
	 * DBUserQuery dbUser = new DBUserQueryImpl(); DBCheckinQuery db = new
	 * DBCheckinQueryImpl();
	 * 
	 * for (int i=0;i<64;i++) { User u = dbUser.getUser(i); Location l =
	 * getRandomLocation(); CheckIn c =new CheckIn(); c.setLocation(l);
	 * c.setUser(u); db.createCheckIn(c); } return CORSResponse.ok("").build();
	 * }
	 * 
	 * 
	 * private Location getRandomLocation() { int x=(Math.random()<0.5)?0:1;
	 * DBLocationQuery db = new DBLocationQueryImpl();
	 * 
	 * if (x==0) return db.getLocation(7); else return db.getLocation(9); }
	 */
}
