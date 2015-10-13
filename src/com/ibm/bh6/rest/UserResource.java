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
import com.ibm.bh6.dao.DBUserQuery;
import com.ibm.bh6.dao.impl.DBUserQueryImpl;
import com.ibm.bh6.model.Location;
import com.ibm.bh6.model.User;

@Path("/user")

public class UserResource {

	@GET
	@Path("find")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find() {
		DBUserQuery db = new DBUserQueryImpl();

		return CORSResponse.ok(getJSON(db.getUsers()).toString()).build();
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
		contact.addProperty("mobile", u.getPhoneOffice());
		contact.addProperty("email", u.getEmail());
		jsonObject.add("contact", contact);
		injectFakeSkills(u, jsonObject);

		return jsonObject;
	}

	private void injectFakeSkills(User u, JsonObject j) {
		JsonArray skillArry = new JsonArray();
		JsonObject s;
		if (u != null && u.getJobDesc() != null) {
			if (u.getJobDesc().toLowerCase().contains("architekt")) {
				s = new JsonObject();
				s.addProperty("id", "1");
				s.addProperty("name", "IT Architektur");
				s.addProperty("level", "2");
				skillArry.add(s);
			}

			if (u.getJobDesc().toLowerCase().contains("consulting")) {
				s = new JsonObject();
				s.addProperty("id", "2");
				s.addProperty("name", "Consulting");
				s.addProperty("level", "2");
				skillArry.add(s);
			}

			if (u.getJobDesc().toLowerCase().contains("websphere") || u.getJobDesc().toLowerCase().contains("was")) {
				s = new JsonObject();
				s.addProperty("id", "3");
				s.addProperty("name", "WebSphere");
				s.addProperty("level", "2");
				skillArry.add(s);
			}

			if (u.getJobDesc().toLowerCase().contains("websphere")) {
				s = new JsonObject();
				s.addProperty("id", "4");
				s.addProperty("name", "Java");
				s.addProperty("level", "2");
				skillArry.add(s);
			}

			if (u.getJobDesc().toLowerCase().contains("db2") || u.getJobDesc().toLowerCase().contains("database")) {
				s = new JsonObject();
				s.addProperty("id", "5");
				s.addProperty("name", "DB2");
				s.addProperty("level", "2");
				skillArry.add(s);
			}
		}

		j.add("skills", skillArry);
	}

}
