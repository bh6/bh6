package com.ibm.bh6.rest;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.bh6.dao.DBUserQuery;
import com.ibm.bh6.dao.impl.DBUserQueryImpl;
import com.ibm.bh6.model.User;

@Path("/session")
public class SessionResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/login")
	public Response login(@Context HttpServletRequest request, @QueryParam("user") String user,
			@QueryParam("password") String password) {
		if (checkLogin(request, user, password)) {
			return Response.ok("You are now logged in").build();
		}
		performLogout(request);
		return CORSResponse.ok("Loginfailed").build();

	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getCurrentUser")
	public Response getCurrentUser(@Context HttpServletRequest request) {
		User u;

		Object o = request.getSession().getAttribute("currentUser");
		System.out.println(o);
		if (o != null && o instanceof User) {
			u = (User) o;
			return Response.ok("User: " + u.getUserName()).build();
		}

		return CORSResponse.ok("No current User").build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/logout")
	public Response logout(@Context HttpServletRequest request) {
		performLogout(request);
		return CORSResponse.ok("You are now logged out").build();
	}

	private void performLogout(HttpServletRequest request) {
		request.getSession().removeAttribute("currentUser");
		request.getSession().invalidate();
	}

	private boolean checkLogin(HttpServletRequest request, String user, String password) {
		DBUserQuery db = new DBUserQueryImpl();
		User u = db.getUsersByName(user).get(0); // TODO Noch eine Passwort prüfung
		System.out.println("User: "+u);
		if (u != null && u instanceof User) {
			request.getSession().setAttribute("currentUser", u);
			return true;
		}
		return false;
	}

}
