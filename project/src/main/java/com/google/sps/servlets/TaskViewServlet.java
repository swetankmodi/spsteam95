package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/** Servlet facilitating viewing task details. */
@WebServlet("/task")
public class TaskViewServlet extends HttpServlet {
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  private PreparedQuery pq;
  private Query query;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    long taskId = Long.parseLong(getParameter(request, "taskId", "-1"));
    
    if (taskId == -1)
      return;
    
    Entity entity;

    try {
      entity = datastore.get(KeyFactory.createKey("Task", taskId));
    } catch (Exception e) {
      System.out.println(e);
      return;
    }
    Task task = Task.getTaskFromDatastoreEntity(entity);
    List<Long> taskAssigneeList = new ArrayList<>();

    //get user id
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      return;
    }
    String userEmail = userService.getCurrentUser().getEmail();
    Gson gson = new Gson();
    JsonObject taskData = new JsonObject();
    Filter emailFilter = new FilterPredicate("email", FilterOperator.EQUAL, userEmail);
    Query userQuery = new Query("User").setFilter(emailFilter);
    pq = datastore.prepare(userQuery);

    Entity userEntity = pq.asSingleEntity();
    long userId = userEntity.getKey().getId();

    if(task.getCreatorId() == userId && !task.isAssigned()) {
      Filter taskIdFilter = new FilterPredicate("taskId", FilterOperator.EQUAL, task.getId());
      query = new Query("TaskApplicants").setFilter(taskIdFilter);
      PreparedQuery results = datastore.prepare(query);
      for (Entity assigneeEntity : results.asIterable()) {
        taskAssigneeList.add(Long.parseLong(assigneeEntity.getProperty("applicantId").toString()));
      }
      //Dummy data
    }
    taskData.add("task", gson.toJsonTree(task));
    taskData.add("taskAssigneeList", gson.toJsonTree(taskAssigneeList));
    System.out.println(taskAssigneeList);
    System.out.println(taskData);
    response.setContentType("application/json;");
    response.getWriter().println(taskData);
  }

  /**
   * If present, get the request parameter identified by name, else return defaultValue.
   *
   * @param request The HTTP Servlet Request.
   * @param name The name of the rquest parameter.
   * @param defaultValue The default value to be returned if required parameter is unspecified.
   * @return The request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}
