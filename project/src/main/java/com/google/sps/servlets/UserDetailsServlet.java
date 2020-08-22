package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/profile")
public class UserDetailsServlet extends HttpServlet {
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  /**
   * @return JSON response with the user-details of the current logged-in user.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long userId = getParameter(request, "userId", -1L);
    
    if (userId == -1)
      return;
    
    User user = User.getUserFromId(userId);
    if (user == null) {
      // Logged in user is not registered in Datastore
      return;
    }

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      return;
    }
    String userEmail = userService.getCurrentUser().getEmail();
    User loggedInUser = User.getUserFromEmail(userEmail);
    long loggedInId = loggedInUser.getId();

    JsonObject userData = new JsonObject();
    Gson gson = new Gson();
    userData.add("user", gson.toJsonTree(user));
    userData.addProperty("loggedInUserId", loggedInId);
    userData.addProperty("canEditProfile", loggedInId == userId);
    userData.addProperty("userLogoutUrl", userService.createLogoutURL("/"));
    response.setContentType("application/json;");
    response.getWriter().println(userData);
  }

  private Long getParameter(HttpServletRequest request, String name, Long defaultValue) {
    Long value = Long.parseLong(request.getParameter(name));
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}
