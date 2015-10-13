<%@ page import="com.ibm.bh6.dao.*" %>
<%@ page import="com.ibm.bh6.dao.impl.*" %>
<%@ page import="com.ibm.bh6.model.*" %>

<%!


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
	
	private void performLogout(HttpServletRequest request) {
		request.getSession().removeAttribute("currentUser");
		request.getSession().invalidate();
	}
%>
<%
String user=request.getParameter("username");
String password=request.getParameter("password");
if (checkLogin(request, user, password)) {
			 String redirectURL = "checkin.html";
    		 response.sendRedirect(redirectURL);
			return;
		}
		performLogout(request);

%>Login failed