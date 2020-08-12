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
    UserService userService = UserServiceFactory.getUserService();
    
    String userEmail = userService.getCurrentUser().getEmail();
    Filter emailFilter = new FilterPredicate("email", Query.FilterOperator.EQUAL, userEmail);
    Query query = new Query("User").setFilter(emailFilter);
    PreparedQuery pq = datastore.prepare(query);
    Entity userEntity = pq.asSingleEntity();

    String name = userEntity.getProperty("name").toString();
    String email = userEntity.getProperty("email").toString();
    String phone = userEntity.getProperty("phone").toString();
    float rating = Float.parseFloat(userEntity.getProperty("rating").toString());
    User user = new User(name, email, phone, rating);

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(user));
  }

}
