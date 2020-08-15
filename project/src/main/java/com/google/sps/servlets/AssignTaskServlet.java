package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.Task;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

/** Servlet facilitating assign task. */
@WebServlet("/task/assign")
public class AssignTaskServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String taskId = getParameter(request, "taskId", "");
    if (taskId.equals(""))
      return;

    long assigneeId = getParameter(request, "assigneeId", -1);
    
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
        return;
    }

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Filter taskIdFilter = new FilterPredicate("taskId", Query.FilterOperator.EQUAL, taskId);
    Query query = new Query("Task").setFilter(taskIdFilter);
    PreparedQuery pq = datastore.prepare(query);

    Entity taskEntity = pq.asSingleEntity();

    Task task = Task.getTaskFromDatastoreEntity(taskEntity);
    task.setAssigneeId(assigneeId);

    /*TODO:
        response to be redirected to the task_view page
    */
    response.sendRedirect("/index.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
  private long getParameter(HttpServletRequest request, String name, long defaultValue) {
    long value = Long.parseLong(request.getParameter(name));
    if (value == -1) {
      return defaultValue;
    }
    return value;
  }
}