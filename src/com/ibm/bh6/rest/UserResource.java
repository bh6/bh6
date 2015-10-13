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

	private static final int maxUserResults = 50;

	@GET
	@Path("debugFind")
	@Produces(MediaType.TEXT_PLAIN)
	public Response findDebug(@QueryParam("x") float x, @QueryParam("y") float y) {
		DBCheckinQuery dbCheckin = new DBCheckinQueryImpl();
		List<CheckIn> checkins = dbCheckin.getClosestCheckIns(x, y);

		String s = "";

		for (CheckIn checkIn : checkins) {
			s += checkIn.getLocation().getGPSx() + "/" + checkIn.getLocation().getGPSx() + "\n";

		}
		return CORSResponse.ok(s).build();
	}

	@GET
	@Path("find")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@QueryParam("text-basic") String searchstring, @QueryParam("x") float x,
			@QueryParam("y") float y) {
		if (x < 47 || x > 52) {
			x = 48.6f;
		}

		if (y < 8 || y > 10) {
			y = 9.03f;
		}

		DBUserQuery db = new DBUserQueryImpl();
		DBCheckinQuery dbCheckin = new DBCheckinQueryImpl();

		// Get Checkins by current location
		List<CheckIn> checkins = dbCheckin.getClosestCheckIns(x, y);
		List<CheckIn> checkinsFinal = new ArrayList<CheckIn>();
		System.out.println("DB suche liefert " + checkins.size() + " Ergebnisse");

		if (checkins != null && searchstring != null && searchstring.length() > 0) {
			String[] searchTokens = searchstring.toLowerCase().split(" ");
			if (searchTokens.length > 0) {
				for (CheckIn thisCheckIn : checkins) {
					if (userMatchFilter(searchTokens, thisCheckIn))
						checkinsFinal.add(thisCheckIn);
				}
			}

		} else {
			checkinsFinal = checkins;
		}
		return CORSResponse.ok(getJSON(checkinsFinal).toString()).build();
	}

	private boolean userMatchFilter(String[] searchtokens, CheckIn checkin) {
		User u = checkin.getUser();
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

	private JsonElement getJSON(List<CheckIn> checkins) {
		JsonArray resultArray = new JsonArray();
		int i = 0;
		for (Iterator<CheckIn> it = checkins.iterator(); it.hasNext();) {
			System.out.println("iteration");
			CheckIn currCheckin = it.next();
			User currUser = currCheckin.getUser();
			JsonElement e = getJSON(currUser, currCheckin);
			resultArray.add(e);
			i++;
			if (i == maxUserResults)
				return resultArray;
		}

		return resultArray;
	}

	public JsonElement getJSON(User u, CheckIn c) {

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

		if (c != null) {
			JsonObject lastCheckinLocation = new JsonObject();
			lastCheckinLocation.addProperty("id", c.getCheckInId());
			lastCheckinLocation.addProperty("name", c.getLocation().getName());
			lastCheckinLocation.addProperty("type", c.getLocation().getLocType());

			JsonObject gps = new JsonObject();
			gps.addProperty("x", c.getLocation().getGPSx());
			gps.addProperty("y", c.getLocation().getGPSy());
			lastCheckinLocation.add("gps", gps);

			JsonObject adr = new JsonObject();
			adr.addProperty("street", c.getLocation().getStreet());
			adr.addProperty("number", c.getLocation().gethnr());
			adr.addProperty("plz", c.getLocation().getBrick());
			adr.addProperty("stadt", c.getLocation().getCity());
			lastCheckinLocation.add("adress", adr);

			jsonObject.add("lastcheckinglocation", lastCheckinLocation);
		}

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
