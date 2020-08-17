package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/login-status")
public class LoginStatusServlet extends HttpServlet {
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  /**
   * @return JSON response with the status of the current logged-in user and if the user details are complete.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();
    JsonObject status = new JsonObject();

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      status.addProperty("loggedIn", true);
      String userEmail = userService.getCurrentUser().getEmail();
      String urlToRedirectToAfterUserLogsOut = "/index.html";
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
      status.addProperty("logoutUrl", logoutUrl);
      status.addProperty("email", userEmail);

      User loggedInUser = User.getUserFromEmail(userEmail);

      if (loggedInUser == null) {
        // User with given email does not exist in Datastore,
        // add a User with dummy user details to datastore
        Entity userEntity = new Entity("User");
        userEntity.setProperty("name", "");
        userEntity.setProperty("email", userEmail);
        userEntity.setProperty("phone", "");
        userEntity.setProperty("rating", 0.0);
        datastore.put(userEntity);
        status.addProperty("editDetails", true);
      }
      else if(loggedInUser.getName().length() > 0) {
        //If the current user has non-dummy details
        status.addProperty("editDetails", false);
        status.addProperty("userId", loggedInUser.getId());
      }
      else{
        //If the current user already exists but has dummy details
        status.addProperty("editDetails", true);
      }
    }

    else {
      status.addProperty("loggedIn", false);
      String urlToRedirectToAfterUserLogsIn = "/index.html";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
      status.addProperty("loginUrl", loginUrl);
    }

    response.setContentType("application/json;");
    response.getWriter().println(status);
  }
}

