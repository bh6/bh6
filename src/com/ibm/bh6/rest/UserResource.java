package com.ibm.bh6.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ibm.bh6.model.Location;
import com.ibm.bh6.model.User;

@Path("/user")

public class UserResource {

	@GET
	@Path("find")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find() {
		ArrayList<User> users = new ArrayList<User>();
		User u = new User();
		users.add(u);
		
		
		return Response.ok(getJSON(users).toString()).build();
	}

	private JsonElement getJSON(Collection<User> users) {
		JsonArray resultArray = new JsonArray();

		for (Iterator<User> it = users.iterator(); it.hasNext();) {
			JsonElement e = getJSON((User) it.next());
			resultArray.add(e);
		}

		return resultArray;
	}

	public JsonElement getJSON(User u) {

		if (u == null) {
			return new JsonObject();
		}

		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("id", u.getUserId());
		jsonObject.addProperty("name", u.getUserName());
		jsonObject.addProperty("title", u.getJobDesc());
		jsonObject.addProperty("imgurl", u.getUserImg());
		
		JsonObject contact = new JsonObject();
		contact.addProperty("mobile",u.getPhoneOffice());
		contact.addProperty("email", u.getEmail());
		jsonObject.add("contact", contact);

		return jsonObject;
	}

}