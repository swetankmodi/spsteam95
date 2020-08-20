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
import javax.servlet.ServletException;
import com.google.gson.Gson;

@WebServlet("/profile/edit")
public class EditUserDetailsServlet extends HttpServlet {
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.sendRedirect("/edit-profile.jsp");
  }


  /**
   * @param POST request to edit user details
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    String userEmail = userService.getCurrentUser().getEmail();
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String phone = request.getParameter("phone");
    float rating = Float.parseFloat(request.getParameter("rating"));

    if(!userEmail.equals(email)){
      return;
    }

    Filter emailFilter = new FilterPredicate("email", Query.FilterOperator.EQUAL, userEmail);
    Query query = new Query("User").setFilter(emailFilter);
    PreparedQuery pq = datastore.prepare(query);

    Entity userEntity = pq.asSingleEntity();
    userEntity.setProperty("name", name);
    userEntity.setProperty("phone", phone);

    datastore.put(userEntity);
    response.sendRedirect("/index.html");
  }
}
