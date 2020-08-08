package com.google.sps.servlets;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/user-details")
public class UserDetailsServlet extends HttpServlet {

  /**
   * @return JSON response with the user-details of the current logged-in user.
   */

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    Datastore datastore = DatastoreOptions.newBuilder().setProjectId("summer20-sps-95").build().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("User");
    String userEmail = userService.getCurrentUser().getEmail();
    Key key = keyFactory.newKey(userEmail);
    Entity getEntity = datastore.get(key);

    User user = new User(getEntity.getString("name"), getEntity.getString("email"), 
        getEntity.getString("phone"), getEntity.getString("rating"));

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(user));
  }

  /**
   * @param POST request to edit user details
   */
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    String userEmail = userService.getCurrentUser().getEmail();;
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String phone = request.getParameter("phone");
    String rating = request.getParameter("rating");

    if(!userEmail.equals(email)){
      return;
    }

    Datastore datastore = DatastoreOptions.newBuilder().setProjectId("summer20-sps-95").build().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("User");
    Key key = keyFactory.newKey(userEmail);
    Entity userEntity = Entity.newBuilder(key)
            .set("name", name)
            .set("email", email)
            .set("phone", phone)
            .set("rating", rating)
            .build();
    datastore.put(userEntity);
    response.sendRedirect("/index.html");
  }
}
