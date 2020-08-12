package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.Task;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

/** Servlet facilitating listing of tasks. */
@WebServlet(urlPatterns = {"/task/all", "/task/created", "/task/assigned"})
public class TaskListServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String uriInfo = request.getRequestURI();
    UserService userService = UserServiceFactory.getUserService();
    
    if (!userService.isUserLoggedIn()) {
      return;
    }

    // Prepare the Query
    Query query = new Query("Task").addSort("creationTime", SortDirection.DESCENDING);
    if (uriInfo.equals("/task/created")) {
      Filter creatorFilter = new FilterPredicate("creatorId", FilterOperator.EQUAL, 0);
      query.setFilter(creatorFilter);
    } else if (uriInfo.equals("/task/completed")) {
      //form filters for completed tasks
      Filter creatorFilter = new FilterPredicate("creatorId", FilterOperator.EQUAL, -1);
      query.setFilter(creatorFilter);
    }

    // Query the Datastore for the required tasks
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Task> tasks = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      Task task = Task.getTaskFromDatastoreEntity(entity);
      tasks.add(task);
    }

    // Send the JSON as the response
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(tasks));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    doGet(request, response);
  }

}

