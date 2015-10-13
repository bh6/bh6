package com.ibm.bh6.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ibm.bh6.dao.DBCheckinQuery;
import com.ibm.bh6.dao.DBUserQuery;
import com.ibm.bh6.dao.impl.DBCheckinQueryImpl;
import com.ibm.bh6.dao.impl.DBUserQueryImpl;
import com.ibm.bh6.model.CheckIn;
import com.ibm.bh6.model.Location;
import com.ibm.bh6.model.User;

@Path("/user")

public class UserResource {

	@GET
	@Path("find")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@QueryParam("text-basic") String searchstring, @QueryParam("x") float x,
			@QueryParam("y") float y) {
		ArrayList<User> resultList = new ArrayList<User>();
		if (x < 47 || x > 52) {
			x = 48.6f;
		}

		if (y < 8 || y > 10) {
			y = 9.03f;
		}

		DBUserQuery db = new DBUserQueryImpl();
		DBCheckinQuery dbCheckin = new DBCheckinQueryImpl();

		// old List<User> c = db.getUsers();

		// Get Checkins by current location
		List<CheckIn> checkins = dbCheckin.getClosestCheckIns(x, y);

		HashMap<User, CheckIn> c = new HashMap<User, CheckIn>();
		if (checkins != null) {

			// Get Users from this checkins to list "c"

			for (CheckIn checkin : checkins) {
				c.put(checkin.getUser(), checkin);
			}

			if (searchstring != null && searchstring.length() > 0) {
				String[] searchTokens = searchstring.toLowerCase().split(" ");

				if (searchTokens.length > 0) {
					HashMap<User, CheckIn> oldList = c;
					c = new HashMap<User, CheckIn>();
					for (User user : oldList.keySet()) {
						if (userMatchFilter(searchTokens, user, oldList.get(user)))
							c.put(user, oldList.get(user));
					}
				}
			}

			// TODO: Now limit to 5
			HashMap<User, CheckIn> completeList = c;
			resultList = new ArrayList<User>();
			for (int i = 0; i < 5; i++) {
				resultList.add((User) completeList.keySet().toArray()[i]);
			}

		}
		return CORSResponse.ok(getJSON(resultList).toString()).build();
	}

	private boolean userMatchFilter(String[] searchtokens, User u, CheckIn checkin) {
		boolean allTokensFound = true;
		for (int i = 0; i < searchtokens.length && allTokensFound; i++) {
			String currentToken = searchtokens[i];
			// Check if this token is present in the User

			if (u.getUserName() != null && u.getUserName().toLowerCase().indexOf(currentToken) > -1)
				continue;
			if (u.getJobDesc() != null && u.getJobDesc().toLowerCase().indexOf(currentToken) > -1)
				continue;

			if (checkin != null && checkin.getLocation() != null
					&& checkin.getLocation().getName().toLowerCase().indexOf(currentToken) > -1)
				continue;
			
			
			allTokensFound = false;
		}

		return allTokensFound;
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
