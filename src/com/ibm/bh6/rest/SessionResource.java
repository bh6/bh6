package com.ibm.bh6.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/session")
public class SessionResource {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/login")
	public Response login(@Context HttpServletRequest request,@QueryParam("user") String user, @QueryParam("password") String password) {
		if (checkLogin(request,user, password)) {
			return Response.ok("You are now logged in").build();
		}
		performLogout(request);
		return Response.ok("Loginfailed").build();

	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getCurrentUser")
	public Response getCurrentUser(@Context HttpServletRequest request) {
		String user = "";
		
		Object u = request.getSession().getAttribute("currentUser");
		if (u!=null) user = (String) u;
		
		return Response.ok("User: "+user).build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/logout")
	public Response logout(@Context HttpServletRequest request) {
		performLogout(request);
		return Response.ok("You are now logged out").build();
	}

	private void performLogout(HttpServletRequest request) {
		request.getSession().invalidate();
	}

	private boolean checkLogin(HttpServletRequest request,String user, String password) {
		request.getSession().setAttribute("currentUser", user);
		return true;
	}
	
	

}
